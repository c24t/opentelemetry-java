/*
 * Copyright 2019, OpenTelemetry Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.opentelemetry.opencensusshim.tags.propagation;

import io.opentelemetry.opencensusshim.tags.TagContext;

/**
 * Object containing all supported {@link TagContext} propagation formats.
 *
 * @since 0.1.0
 */
public abstract class TagPropagationComponent {

  /**
   * Returns the {@link TagContextBinarySerializer} for this implementation.
   *
   * @return the {@code TagContextBinarySerializer} for this implementation.
   * @since 0.1.0
   */
  public abstract TagContextBinarySerializer getBinarySerializer();

  /**
   * Returns the {@link TagContextTextFormat} for this implementation.
   *
   * <p>OpenCensus uses W3C Correlation Context as the HTTP text format. For more details, see <a
   * href="https://github.com/w3c/correlation-context">correlation-context</a>.
   *
   * @return the {@code TagContextTextFormat} for this implementation.
   * @since 0.1.0
   */
  public abstract TagContextTextFormat getCorrelationContextFormat();
}
