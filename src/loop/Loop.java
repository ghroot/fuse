package loop;

import com.artemis.*;
import java.util.*;
import loop.component.*;
import loop.system.*;
import fuse.*;

public class Loop implements Runnable {
	private Thread thread;
	private boolean alive = true;
	private World world;

	private List<QueuedRule> queuedRules = new ArrayList<QueuedRule>();

	ComponentMapper<Transform> transformMapper;
	ComponentMapper<Path> pathMapper;
	ComponentMapper<Fuse> fuseMapper;

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
		thread.stop();
		world.dispose();
	}

	public void gameJoined(Router.User user) {
		int entity = world.create();
		transformMapper.create(entity);
		pathMapper.create(entity);
		fuseMapper.create(entity).user = user;
	}

	public void gameLeft(long id) {

	}

	public String handleRule(Router.User user, String[] data) {
		queuedRules.add(new QueuedRule(user, data));
		return data[0] + "|done";
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
