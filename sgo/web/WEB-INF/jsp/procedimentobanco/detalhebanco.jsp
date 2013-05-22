<%@ include file="/header.jspf"%>
	<div id="content-header">
		<h1>Detalhe Bancos</h1>
		<div class="btn-group">
			<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Comments"><i class="icon-comment"></i><span class="label label-important">5</span></a>
			<a class="btn btn-large tip-bottom" title="Manage Orders"><i class="icon-shopping-cart"></i></a>
		</div>
	</div>
	
	<div id="breadcrumb">
		<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a>
		<a href="#" class="current">Procedimentos</a>
	</div>
	
	<c:if test="${not empty notice}">
		<c:choose>
			<c:when test="${fn:contains(notice,'Erro:')}">
					<div class="alert alert-error">
						<strong>${notice }</strong>
						<a href="#" data-dismiss="alert" class="close">×</a>
					</div>
			</c:when>
			<c:otherwise>
					<div class="alert alert-success">
						<strong>${notice }</strong>
						<a href="#" data-dismiss="alert" class="close">×</a>
					</div>
			</c:otherwise>
		</c:choose>
	</c:if>

<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12">
			<div class="accordion" id="collapse-group">
               	<div class="accordion-group widget-box">
                   	<div class="accordion-heading">
                       	<div class="widget-title">
                           	<a data-parent="#collapse-group" href="#collapseGOne" data-toggle="collapse">
                              	<span class="icon"><i class="icon-magnet"></i></span><h5>Detalhe Bancos</h5>
                          	</a>
                      	</div>
                   	</div>
                   	<div class="collapse in accordion-body" id="collapseGOne">
                       	<div class="widget-content">
                          	<table class="table table-striped table-bordered">
								<thead>
									<tr>
										<th>Nome</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${bancos }" var="banco">
										<tr>
											<td class="label_txt">
												<a href="<c:url value="/procedimentobanco/detalhemodelo/${banco.banco_id}"/>">${banco.nome }</a>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
                      	</div>
                  	</div>
            	</div>
            </div>

		</div>
	</div>
</div>

<%@ include file="/footer.jspf"%>