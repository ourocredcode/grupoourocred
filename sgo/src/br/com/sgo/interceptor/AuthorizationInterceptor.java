/***
 * Copyright (c) 2009 Caelum - www.caelum.com.br/opensource
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.com.sgo.interceptor;

import java.util.Arrays;

import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.resource.ResourceMethod;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.com.sgo.controller.HomeController;
import br.com.sgo.dao.PerfilDao;
import br.com.sgo.dao.UsuarioDao;

@Intercepts
public class AuthorizationInterceptor implements Interceptor {


	private final UsuarioInfo info;
	private final UsuarioDao usuarioDao;
	private final PerfilDao perfilDao;
	private final Result result;

	public AuthorizationInterceptor(UsuarioInfo info, UsuarioDao usuarioDao,PerfilDao perfilDao, Result result) {
		this.info = info;
		this.usuarioDao = usuarioDao;
		this.perfilDao = perfilDao;
		this.result = result;
	}

    public boolean accepts(ResourceMethod method) {
        return !method.containsAnnotation(Public.class);
    }


    public void intercept(InterceptorStack stack, ResourceMethod method, Object resourceInstance)
            throws InterceptionException {

    	if (info.getUsuario() == null) {
    		// remember added parameters will survive one more request, when there is a redirect
    		result.include("errors", Arrays.asList(new ValidationMessage("usuário não logado", "user")));
    		result.redirectTo(HomeController.class).login();
    	} else {

    		usuarioDao.refresh(info.getUsuario());

    		if(info.getPerfil() != null){
    			perfilDao.refresh(info.getPerfil());
    		}

    		// continues execution
    		
	    	stack.next(method, resourceInstance);
    	}
    }
}
