/*
 * Copyright (c) 2014 Takao Nakaguchi
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
package jp.takawitter.robodova.cordova;

import org.robovm.rt.bro.ValuedEnum;

public enum CDVCommandStatus implements ValuedEnum {
    NoResult(0),
    OK(1),
    ClassNotFoundException(2),
    IllegalAccessException(3),
    InstantiationException(4),
    MalformedUrlException(5),
    IOException(6),
    InvalidAction(7),
    JsonException(8),
    Error(9);

    private final long n;

    private CDVCommandStatus(int n) { this.n = n; }
    public long value() { return n; }
}
