/**
 *
 */
package org.purl.accessor.command;

import org.purl.accessor.ResourceStorage;
import org.purl.accessor.URIResolver;
import org.ten60.netkernel.layer1.nkf.INKFConvenienceHelper;
import org.ten60.netkernel.layer1.nkf.INKFResponse;
import org.ten60.netkernel.layer1.nkf.NKFException;
import org.ten60.netkernel.layer1.representation.IAspectNVP;

import com.ten60.netkernel.urii.aspect.IAspectString;

/**
 * @version 1.0, 16 August 2007
 * @author Brian Sletten (brian at http://zepheira.com/)
 *
 *=========================================================================
 *
 *  Copyright (C) 2007 OCLC (http://oclc.org)
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *=========================================================================
 *
 */
abstract public class PURLCommand {

    protected String type;
    protected URIResolver uriResolver;
    protected ResourceStorage resStorage;

    /**
     *
     * @param uriResolver
     */
    protected PURLCommand(String type, URIResolver uriResolver, ResourceStorage resStorage) {
        this.type = type;
        this.uriResolver = uriResolver;
        this.resStorage = resStorage;
    }

	abstract public INKFResponse execute(INKFConvenienceHelper context);

	protected IAspectNVP getParams(INKFConvenienceHelper context) throws NKFException {
		IAspectNVP retValue = null;

		if(context.exists("this:param:param")) {
			retValue = (IAspectNVP) context.sourceAspect("this:param:param", IAspectNVP.class);
			IAspectString param = (IAspectString) context.sourceAspect("this:param:param", IAspectString.class);
			System.out.println(param.getString());
		}

		if(retValue == null) {
			if(context.exists("this:param:param2")) {
				retValue = (IAspectNVP) context.sourceAspect("this:param:param2", IAspectNVP.class);
			}
		}

		return retValue;
	}
}