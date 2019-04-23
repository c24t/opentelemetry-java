/*
 * Copyright 2019, OpenConsensus Authors
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

package openconsensus.opentracingshim;

import io.grpc.Context;
import io.opentracing.Scope;
import io.opentracing.ScopeManager;
import io.opentracing.Span;

@SuppressWarnings("deprecation")
final class ScopeManagerShim implements ScopeManager {
  private final Context.Key<openconsensus.trace.Span> activeSpanKey = Context.key("activeSpan");

  public ScopeManagerShim() {}

  @Override
  public Span activeSpan() {
    openconsensus.trace.Span span = activeSpanKey.get();
    if (span == null) {
      span = openconsensus.trace.BlankSpan.INSTANCE;
    }

    return new SpanShim(span);
  }

  @Override
  public Scope active() {
    throw new UnsupportedOperationException();
  }

  @Override
  @SuppressWarnings("MustBeClosedChecker")
  public Scope activate(Span span) {
    SpanShim spanShim = getSpanShim(span);

    Context spanContext = Context.current().withValue(activeSpanKey, spanShim.getSpan());
    return new ContextScope(spanContext);
  }

  @Override
  public Scope activate(Span span, boolean finishSpanOnClose) {
    throw new UnsupportedOperationException();
  }

  static SpanShim getSpanShim(Span span) {
    if (!(span instanceof SpanShim)) {
      throw new IllegalArgumentException("span is not a valid SpanShim object");
    }

    return (SpanShim) span;
  }

  static final class ContextScope implements Scope {
    Context toActivate;
    Context toRestore;

    public ContextScope(Context toActivate) {
      this.toActivate = toActivate;
      this.toRestore = toActivate.attach();
    }

    @Override
    public Span span() {
      throw new UnsupportedOperationException();
    }

    @Override
    public void close() {
      toActivate.detach(toRestore);
    }
  }
}
