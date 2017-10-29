package fuse;

public interface IGameListener {
  public void gameJoined(Router.User user);
  public void gameLeft(Router.User user);
  public String handleRule(Router.User user, String[] data);
}
