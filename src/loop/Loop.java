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

	private ComponentMapper<Transform> transformMapper;
	private ComponentMapper<Path> pathMapper;
	private ComponentMapper<Fuse> fuseMapper;

	public Loop() {
		WorldConfiguration config = new WorldConfigurationBuilder().with(
			new PathRuleProcessingSystem(),
			new PathSystem(),
			new MakeRuleProcessingSystem(),
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
		for (int entity : subscription.getEntities().getData()) {
			Fuse fuse = fuseMapper.get(entity);
			if (fuse.user.equals(user)) {
				world.delete(entity);
				return;
			}
		}
	}

	public String handleRule(Router.User user, String[] data) {
		String rule = data[0];
		for (BaseSystem system : world.getSystems()) {
			if (system instanceof RuleProcessingSystem) {
				RuleProcessingSystem ruleProcessingSystem = (RuleProcessingSystem) system;
				if (ruleProcessingSystem.canProcessRule(rule)) {
					EntitySubscription subscription = world.getAspectSubscriptionManager().get(Aspect.all(Fuse.class));
					for (int entity : subscription.getEntities().getData()) {
			      Fuse fuse = fuseMapper.get(entity);
			      if (fuse.user.equals(user)) {
							return ruleProcessingSystem.processRule(entity, data);
						}
					}
				}
			}
		}
		return null;
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
