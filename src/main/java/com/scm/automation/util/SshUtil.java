package com.scm.automation.util;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.scm.automation.exception.CoreException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

/**
 * SshUtil class.
 *
 * @author prakash.adak
 * @version 1.0.58
 * @since 1.0.58
 */
public class SshUtil {
  static Session session = null;
  static Channel channel = null;
  static JSch jsch;

  private SshUtil() {}

  /**
   * execute.
   *
   * @param hostname a {@link java.lang.String} object
   * @param username a {@link java.lang.String} object
   * @param password a {@link java.lang.String} object
   * @param command a {@link java.lang.String} object
   * @return a {@link java.lang.String} object
   * @since 1.0.58
   */
  public static String execute(String hostname, String username, String password, String command) {
    jsch = new JSch();

    Scanner scanner = null;
    String result = "";
    try {
      Session session = connect(hostname, username, password);
      channel = session.openChannel("exec");
      ((ChannelExec) channel).setCommand(command);
      InputStream in = channel.getInputStream();
      channel.connect();
      scanner = new Scanner(in).useDelimiter("\\A");
      result = scanner.hasNext() ? scanner.next() : "";
      scanner.close();
    } catch (JSchException | IOException exp) {
      throw new CoreException("Could not connect to Machine ", exp);
    } finally {
      if (scanner != null) {
        scanner.close();
      }
      close();
    }

    return result;
  }

  /**
   * copy.
   *
   * @param hostname a {@link java.lang.String} object
   * @param username a {@link java.lang.String} object
   * @param password a {@link java.lang.String} object
   * @param source a {@link java.lang.String} object
   * @param destination a {@link java.lang.String} object
   * @since 1.0.58
   */
  public static void copy(
      String hostname, String username, String password, String source, String destination) {
    jsch = new JSch();
    try {
      Session session = connect(hostname, username, password);
      channel = session.openChannel("sftp");
      ChannelSftp transferFileObj = (ChannelSftp) channel;
      channel.connect();
      transferFileObj.get(source, destination);
      transferFileObj.disconnect();

    } catch (JSchException | SftpException exp) {
      throw new CoreException(
          "Could not transfer file from : " + source + " to " + destination, exp);
    } finally {
      close();
    }
  }

  // region private methods
  private static Session connect(String hostname, String username, String password) {
    try {
      session = jsch.getSession(username, hostname, 22);

      Properties config = new Properties();
      config.put("StrictHostKeyChecking", "no");
      session.setConfig(config);
      session.setPassword(password);
      session.connect();
    } catch (JSchException e) {
      throw new CoreException(e);
    }
    return session;
  }

  private static void close() {
    channel.disconnect();
    session.disconnect();
  }
}
// endregion
