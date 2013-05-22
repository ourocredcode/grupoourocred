<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="accordion" id="collapse-group">
	<div class="accordion-group widget-box">
		<div class="accordion-heading">
           	<div class="widget-title">
               	<a data-parent="#collapse-group" href="#collapseGOne" data-toggle="collapse">
                  	<span class="icon"><i class="icon-magnet"></i></span><h5>Operador</h5>
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
						<c:forEach items="${procedimentosConferencia }" var="procedimentoConferencia">
							<tr>
								<td>${procedimentoConferencia.nome }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
        	</div>
    	</div>
	</div>
</div> 