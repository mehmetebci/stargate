/*
 * Copyright The Stargate Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.stargate.bridge.codec;

import com.datastax.oss.driver.api.core.type.codec.TypeCodecs;
import edu.umd.cs.findbugs.annotations.NonNull;
import io.stargate.bridge.proto.QueryOuterClass.Value;
import io.stargate.bridge.proto.QueryOuterClass.Value.InnerCase;
import io.stargate.db.schema.Column.ColumnType;
import java.nio.ByteBuffer;

public class DoubleCodec implements ValueCodec {
  @Override
  public ByteBuffer encode(@NonNull Value value, @NonNull ColumnType type) {
    if (value.getInnerCase() != InnerCase.DOUBLE) {
      throw new IllegalArgumentException("Expected double type");
    }
    return TypeCodecs.DOUBLE.encodePrimitive(value.getDouble(), PROTOCOL_VERSION);
  }

  @Override
  public Value decode(@NonNull ByteBuffer bytes, @NonNull ColumnType type) {
    return Value.newBuilder()
        .setDouble(TypeCodecs.DOUBLE.decodePrimitive(bytes, PROTOCOL_VERSION))
        .build();
  }
}