<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>   

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE html>
<html lang="en">
    <head>
        <title>SGO - SISTEMA GRUPO OURO CRED</title>
		<meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css"/>" />
		<link rel="stylesheet" href="<c:url value="/css/bootstrap-responsive.min.css"/>" />
        <link rel="stylesheet" href="<c:url value="/css/unicorn.login.css"/>" />
        <script src="<c:url value="/js/jquery.js"/>"></script>  
        <script src="<c:url value="/js/unicorn.login.js"/>"></script> 
		<script type="text/javascript">
		
		
		jQuery(function($){
			
			$('#loginForm').submit(function() {
				$.ajax({
					data: $(this).serialize()
					, type: $(this).attr('method')
					, url: $(this).attr('action')
					, success: function(response) {
		
						$("#login-li").removeClass("active");					
						$("#login-li").addClass("disabled");				
						$("#login-li-a").attr('href',"#");
			
						$("#perfil-li").removeClass("disabled");
						$("#perfil-li").addClass("active");
						$("#perfil-li-a").attr('href',"#perfil-div");
			
						$("#login-div").removeClass("tab-pane fade active in");
						$("#login-div").addClass("tab-pane fade");
						
						$("#perfil-div").removeClass("tab-pane fade");
						$("#perfil-div").addClass("tab-pane fade active in");
		
						$("#usuarioPerfil").html(response);
		
					}
		
				});
				return false;
			});
			
			$('#usuarioPerfil').change(function() {
				var perfil_id = $("#usuarioPerfil").val();
				$("#usuarioPerfilEmpresa").load('<c:url value="/home/empresas" />',{'perfil_id': perfil_id});
			});
			
			$('#usuarioPerfilEmpresa').change(function() {
		
				var empresa_id = $("#usuarioPerfilEmpresa").val();
				var perfil_id = $("#usuarioPerfil").val();
		
				$("#usuarioPerfilOrganizacao").load('<c:url value="/home/organizacoes" />',{'perfil_id': perfil_id, 'empresa_id':empresa_id});
			});
		
			$('#btnSair').click(function() {
				window.location.href = '<c:url value="/home/logout" />';
			});
		
		}); 
		
		</script>
    </head>
    <body>
        <div id="logo">
           <img src="<c:url value="../img/logo1.png"/>" alt="" />
        </div>
        <div id="loginbox">

            	<ul id="myTab" class="nav nav-tabs">
					<li class="active" id="login-li"><a href="#login-div" data-toggle="tab" id="login-li-a">Login</a></li>
					<li class="disabled" id="perfil-li"><a href="#" data-toggle="tab" id="perfil-li-a">Perfil</a></li>	
				</ul>
	
				<div id="myTabContent" class="tab-content">
	
					<div class="tab-pane fade active in" id="login-div">
	
						<form id="loginForm" action="<c:url value="/home/login"/>" method="POST">					
	
							<div class="control-group">
								<label class="control-label" for="login">Login</label>
								<div class="controls">
									<input type="text" id="login" name="login" placeholder="Digite seu CPF" required>
								</div>
							</div>
	
							<div class="control-group">
								<label class="control-label" for="password">Senha</label>
								<div class="controls">
									<input type="password" id="password" name="password" placeholder="Senha" required>
								</div>
								<div class="control-group">
									<div class="controls">
										<p></p>
										<button type="submit" class="btn btn-primary">OK</button>
									</div>
								</div>
							</div>
						</form>
	
					</div>
	
					<div class="tab-pane fade"  id="perfil-div">
	
						<p></p>
						<form id="usuarioPerfilForm" action="<c:url value="/home/perfil"/>" method="POST">
							<input type="hidden" id="usuarioPerfilUsuarioId" name="usuarioPerfil.usuario.usuario_id" />
							<div class="control-group">
								<label class="control-label" for="usuarioPerfil">Perfil</label>
								<select id="usuarioPerfil" name="usuarioPerfil.perfil.perfil_id">	
									<option value="">Selecione o perfil</option>
								</select>
							</div>
		
							<div class="control-group">
								<label class="control-label" for="usuarioPerfilEmpresa">Empresa</label>
								<div class="controls">
									<select id="usuarioPerfilEmpresa" name="usuarioPerfil.empresa.empresa_id">	
										<option value="">Selecione a empresa</option>
									</select>
								</div>
							</div>
		
							<div class="control-group">
								<label class="control-label" for="usuarioPerfilOrganizacao">Organiza&ccedil;&atilde;o</label>
								<div class="controls">
									<select id="usuarioPerfilOrganizacao" name="usuarioPerfil.organizacao.organizacao_id">	
										<option value="">Selecione a organizacao</option>
									</select>
								</div>
		
								 <div class="btn-toolbar">
									<div class="btn-group">
										<button type="submit" class="btn btn-primary">OK</button>
									</div>	
									<div class="btn-group">
										<button type="button" class="btn btn-primary" id="btnSair" >Sair</button>
									</div>
								</div>					
							</div>
						</form>
	
					</div>
				</div>
        </div>
        

    </body>
</html>
