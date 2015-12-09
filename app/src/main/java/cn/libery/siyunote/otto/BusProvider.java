package cn.libery.siyunote.otto;

import com.squareup.otto.Bus;

/**
 * Maintains a singleton instance for obtaining the bus. Ideally this would be replaced with a more efficient means
 * such as through injection directly into interested classes.
 */
public final class BusProvider {

  /**
   * Since at times it may be ambiguous on which thread you are receiving callbacks,
   * Otto provides an enforcement mechanism to ensure you are always called on the thread you desire.
   * By default, all interaction with an instance is confined to the main thread.
   *
   * If you are not concerned on which thread interaction is occurring,
   * instantiate a bus instance with ThreadEnforcer.ANY.
   * You can also provide your own implementation of the ThreadEnforcer interface
   * if you need additional functionality or validation.
   */
  private static final Bus BUS = new Bus();

  public static Bus getInstance() {
    return BUS;
  }

  private BusProvider() {
    // No instances.
  }

}
