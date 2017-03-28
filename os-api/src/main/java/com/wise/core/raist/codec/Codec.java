/*
 * Copyright 2013 Lei CHEN (raistlic@gmail.com)
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

package com.wise.core.raist.codec;

/**
 * A codec is a combination of encoder and decoder, that is, it can translate
 * between two types in both ways. 
 * 
 * <p/>
 * See also {@link com.wise.core.raist.codec.Encoder}.
 * 
 * <p/>
 * See also {@link com.wise.core.raist.codec.Decoder}.
 *
 * @author Lei.C (2013-12-05)
 */
public interface Codec<S, D> extends Encoder<S, D>, Decoder<S, D> {}