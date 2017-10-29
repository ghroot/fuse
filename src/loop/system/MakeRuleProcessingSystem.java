package loop.system;

import javax.vecmath.*;
import java.util.*;

import com.artemis.*;
import com.artemis.systems.*;

import fuse.*;
import loop.*;
import loop.component.*;

public class MakeRuleProcessingSystem extends RuleProcessingSystem {
  ComponentMapper<Transform> transformMapper;
  ComponentMapper<Fuse> fuseMapper;
  ComponentMapper<Owner> ownerMapper;

	public MakeRuleProcessingSystem() {
		super("make");
	}

  @Override
  public String processRule(int entity, String[] data) {
    Transform transform = transformMapper.get(entity);
    Fuse fuse = fuseMapper.get(entity);

    String itemName = data[2];

    // TODO: Create item entity
    int itemEntity = world.create();
    transformMapper.create(itemEntity);
    ownerMapper.create(itemEntity).user = fuse.user;

    try {
      // TODO: Find way to push data to fuse without changing access levels
      fuse.user.room.send(fuse.user, "made|" + fuse.user.name + "|" + itemName + ";" + transform.position.x + "," + transform.position.y + ",0", true);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return "make|done";
	}
}
