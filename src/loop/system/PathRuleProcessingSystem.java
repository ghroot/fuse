package loop.system;

import javax.vecmath.*;
import java.util.*;

import com.artemis.*;
import com.artemis.systems.*;

import fuse.*;
import loop.*;
import loop.component.*;

public class PathRuleProcessingSystem extends RuleProcessingSystem {
  ComponentMapper<Path> pathMapper;

	public PathRuleProcessingSystem() {
		super("path");
	}

  @Override
  public String processRule(int entity, String[] data) {
    Path path = pathMapper.get(entity);
    String[] pos = data[2].split(",");
    path.targetPosition.x = Float.parseFloat(pos[0]);
    path.targetPosition.y = Float.parseFloat(pos[1]);
    return "path|done";
	}
}
