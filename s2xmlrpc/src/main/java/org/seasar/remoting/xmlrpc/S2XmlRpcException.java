/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.remoting.xmlrpc;

import org.seasar.framework.exception.SRuntimeException;

/**
 * S2XML-RPCのクライアントでエラーが発生した際にスローされる例外です。
 * @author agata
 */
public class S2XmlRpcException extends SRuntimeException {

	private static final long serialVersionUID = 6031346114420604710L;

	/**
	 * コンストラクタ
	 * @param code メッセージコード
	 */
	public S2XmlRpcException(final String code) {
        super(code);
    }

	/**
	 * コンストラクタ
	 * @param code メッセージコード
	 * @param args メッセージに埋め込まれるの引数
	 */
    public S2XmlRpcException(final String code, final Object[] args) {
        super(code, args);
    }

    /**
	 * コンストラクタ
	 * @param code メッセージコード
	 * @param args メッセージに埋め込まれるの引数
     * @param cause 元の例外
     */
    public S2XmlRpcException(final String code, final Object[] args, final Throwable cause) {
        super(code, args, cause);
    }
}
