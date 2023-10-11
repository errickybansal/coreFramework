package com.scm.automation.ui.page.model;

import lombok.Builder;
import lombok.Data;

/**
 * Request class.
 *
 * @author vipin.v
 * @version 1.0.92
 * @since 1.0.92
 */
@Data
@Builder
public class Request {
  Object[] args;
}
