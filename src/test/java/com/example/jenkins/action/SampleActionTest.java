package com.example.jenkins.action;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SampleActionTest {
  @Test
  @DisplayName("Test check normal.")
  public void testCheckNormal() {
    SampleAction action = new SampleAction();
    HttpServletRequest request = createMock(HttpServletRequest.class);

    expect(request.getParameter("FirstName")).andReturn("firstName");
    expect(request.getParameter("LastName")).andReturn("lastName");

    replay(request);
    boolean result = action.checkParameter(request);
    verify(request);

    assertEquals(true, result);
  }

  @Test
  public void testCheckError1() {
    SampleAction action = new SampleAction();
    HttpServletRequest request = createMock(HttpServletRequest.class);

    expect(request.getParameter("FirstName")).andReturn(null);

    replay(request);
    boolean result = action.checkParameter(request);
    verify(request);

    assertEquals(false, result);
  }

  @Test
  public void testCheckError2() {
    HttpServletRequest request = createMock(HttpServletRequest.class);
    SampleAction action = new SampleAction();

    expect(request.getParameter("FirstName")).andReturn("firstName");
    expect(request.getParameter("LastName")).andReturn(null);

    replay(request);
    boolean result = action.checkParameter(request);
    verify(request);

    assertEquals(false, result);
  }

  @Test
  public void testCheckError3() {
    SampleAction action = new SampleAction();
    HttpServletRequest request = createMock(HttpServletRequest.class);

    expect(request.getParameter("FirstName")).andReturn("");

    replay(request);
    boolean result = action.checkParameter(request);
    verify(request);

    assertEquals(false, result);
  }

  @Test
  public void testExecuteNormal() {
    SampleAction action = new SampleAction("firstName", "lastName");
    HttpServletRequest request = createMock(HttpServletRequest.class);
    HttpSession session = createMock(HttpSession.class);

    expect(request.getSession(true)).andReturn(session);

    replay(request);
    String result = action.execute(request);
    verify(request);

    assertTrue("./WEB-INF/result.jsp".equals(result));
  }
}
