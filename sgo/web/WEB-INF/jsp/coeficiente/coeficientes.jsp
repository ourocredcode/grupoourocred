<%@ include file="/header.jspf"%>

	<script type="text/javascript">
	
	jQuery(function($){ 
		
		$('.data-table').dataTable({
			"bJQueryUI": true,
			"sPaginationType": "full_numbers",
			"sDom": '<""l>t<"F"fp>',
			"bFilter": false
		});

		$('input[type=checkbox],input[type=radio],input[type=file]').uniform();
		
		$('select').select2();

		$("span.icon input:checkbox, th input:checkbox").click(function() {
			var checkedStatus = this.checked;
			var checkbox = $(this).parents('.widget-box').find('tr td:first-child input:checkbox');		
			checkbox.each(function() {
				this.checked = checkedStatus;
				if (checkedStatus == this.checked) {
					$(this).closest('.checker > span').removeClass('checked');
				}
				if (this.checked) {
					$(this).closest('.checker > span').addClass('checked');
				}
			});
		});
		
		
		
	});

	</script>

<div id="content-header">

		<h1>Lista de Coeficientes</h1>

		<div class="btn-group">
			<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
		</div>

	</div>
	
	<div id="breadcrumb">
		<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a>
		<a href="#" class="current">Coeficientes</a>
	</div>

<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12">

			<div id="myTabContent" class="tab-content">
			
			<div class="widget-box">
					<div class="widget-title">
						<span class="icon"><i class="icon-signal"></i> </span>
						<h5>Coeficientes</h5>
					</div>
					<div class="widget-content">

							<h5>Coeficientes</h5>
							<table class="table table-bordered table-striped table-hover data-table" >
							<thead>
							  <tr>
								<th>Data</th>
								<th>Banco</th>
								<th>Tabela</th>
								<th>Valor</th>
								<th>Meta</th>
							  </tr>
							</thead>
							<tbody>
								<c:forEach var="coeficiente" items="${coeficientes }">
									<tr>
										<td><fmt:formatDate value="${coeficiente.created.time}" pattern="dd/MM/yyyy" /></td>
										<td>${coeficiente.banco.nome }</td>
										<td>${coeficiente.tabela.nome }</td>
										<td><fmt:formatNumber value="${coeficiente.valor}" pattern="0.00000" /></td>
										<td><fmt:formatNumber value="${coeficiente.percentualMeta}" pattern="0%" /></td>
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



<%@ include file="/footer.jspf"%>