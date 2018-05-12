package apps.projects;

import apps.people.Member;

public class Task extends Base
{

  private Member executor;

  public Task(String t)
  {
    super(t);
  }

  public Task(String t, Member e)
  {
    super(t);
    executor = e;
  }

  public void assigTo(Member to)
  {
    executor = to;
  }

  public Member getExecutor()
  {
    return executor;
  }
}

