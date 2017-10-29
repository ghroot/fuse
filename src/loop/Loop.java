package loop;

import java.util.*;

import fuse.*;

import com.artemis.*;

import loop.component.*;
import loop.system.*;

public class Loop implements IGameListener, Runnable {
	private Thread thread;
	private boolean alive = true;
	private World world;

	private List<QueuedRule> queuedRules = new ArrayList<QueuedRule>();

	private ComponentMapper<Transform> transformMapper;
	private ComponentMapper<Path> pathMapper;
	private ComponentMapper<Fuse> fuseMapper;

	public Loop() {
		WorldConfiguration config = new WorldConfigurationBuilder().with(
			new PathSystem(queuedRules),
			new PositionSyncSystem()
		).build();
		world = new World(config);
		world.inject(this);
	}

	public void start() {
		thread = new Thread(this);
		thread.start();
	}

	public void stop() {
		alive = false;
		thread.stop();
		world.dispose();
	}

	public void gameJoined(Router.User user) {
		int entity = world.create();
		transformMapper.create(entity);
		pathMapper.create(entity);
		fuseMapper.create(entity).user = user;
	}

	public void gameLeft(Router.User user) {
		EntitySubscription subscription = world.getAspectSubscriptionManager().get(Aspect.all(Fuse.class));
		int entity = subscription.getEntities().get(0);
		world.delete(entity);
	}

	public String handleRule(Router.User user, String[] data) {
		EntitySubscription subscription = world.getAspectSubscriptionManager().get(Aspect.all(Fuse.class));
		if (subscription.getEntities().isEmpty()) {
			return data[0] + "|fail|internal error: entity not found";
		}
		else {
			queuedRules.add(new QueuedRule(user, data));
			return data[0] + "|done";
		}
	}

	public void run() {
		try {
			while(alive) {
				world.setDelta(167);
				world.process();
				Thread.sleep(167);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
