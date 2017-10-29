package loop.system;

import fuse.*;
import com.artemis.*;

public abstract class RuleProcessingSystem extends BaseSystem {
  private String rule;

  public RuleProcessingSystem(String rule) {
    this.rule = rule;
  }

  @Override
  public void processSystem() {}

  public boolean canProcessRule(String rule) {
    return this.rule.equals(rule);
  }

  public abstract String processRule(int entity, String[] data);
}
