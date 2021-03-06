<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
					
<c:if test="${not empty parceiroBeneficios}">

	<table class="table table-striped table-bordered" id="lista">
		<thead>
			<tr>
				<th>Conv�nio</th>
				<th>Matr�cula</th>
				<!-- 
				<th>Excluir</th>
				 -->
				 <c:if test="${parceiroNegocio.parceiroNegocio_id  != null }">
					<th>Hiscon</th>
					<th>Contrato</th>
				</c:if>
			</tr>
		</thead>
		<tbody>	
			<c:forEach items="${parceiroBeneficios}" var="parceiroBeneficio" varStatus="status">
				<tr>
					<td><input type="text" id="parceiroConvenioNome" name="parceiroBeneficios[${status.index}].convenio.nome" value="${parceiroBeneficio.convenio.nome }" class="input-small" onChange="return alteraBeneficioDESATIVADO(this,'convenio.convenio_id','${parceiroBeneficio.convenio.convenio_id }', this.value);" /></td>
					<td><input type="text" id="parceiroBeneficioNumeroLista" name="parceiroBeneficios[${status.index}].numeroBeneficio" value="${parceiroBeneficio.numeroBeneficio }" class="input-small" onChange="return alteraBeneficioDESATIVADO(this,'nome','${parceiroBeneficio.parceiroBeneficio_id}', this.value);" readonly="readonly" /></td>
					<!-- 
					<td style="text-align: center;">
						<button type="button" class="btn btn-danger btn-mini" onClick="return excluiBeneficio(this,'${parceiroBeneficio.parceiroBeneficio_id}');">Excluir</button>
					</td>
					 -->
					 <c:if test="${parceiroNegocio.parceiroNegocio_id  != null }">
						<td style="text-align: center;">
							
							<div class="buttons">
								<a href="#" class="btn btn-mini" onclick="novoHiscon('${parceiroBeneficio.numeroBeneficio}');"><i class="icon-search"></i> Hiscon </a>
							</div>
							
						</td>
						<td style="text-align: center;">
						
							<div class="buttons">
								<a href="#" class="btn btn-mini" onclick="novoContrato('${parceiroBeneficio.numeroBeneficio}');"><i class="icon-file"></i> Contrato </a>
							</div>

						</td>
					</c:if>
				</tr>
			</c:forEach>
			
			<tr>
				<td>
					<select id="parceiroBeneficioConvenio" name="parceiroBeneficioConvenio" >
						<c:forEach var="convenio" items="${convenios }">
							<option value="${convenio.convenio_id }">${convenio.nome }</option>
						</c:forEach>
					</select>
				</td>
				<td><input type="text" id="parceiroBeneficioNumeroNovo" value="${parceiroBeneficio.numeroBeneficio }" class="input-medium" /></td>
				<td style="text-align: center;">
					<button type="button" class="btn btn-mini" id="bttParceiroBeneficioNovo" onClick="return salvaBeneficio();">Novo</button>
				</td>
			</tr>
	
		</tbody>
	</table>

</c:if>

