package loop;

import fuse.*;

public class QueuedRule {
	public Router.User user;
	public String[] data;

  public QueuedRule(Router.User user, String[] data) {
    this.user = user;
    this.data = data;
  }
}
