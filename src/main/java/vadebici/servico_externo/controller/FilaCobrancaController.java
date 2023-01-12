package vadebici.servico_externo.controller;

import vadebici.servico_externo.form.CobrancaForm;
import io.javalin.http.Context;
import vadebici.servico_externo.dom.CobrancaD;
import vadebici.servico_externo.dom.FilaCobranca;
import vadebici.servico_externo.pagamento.Cobranca;

public class FilaCobrancaController {
	
	private FilaCobranca filaCobranca = new FilaCobranca();
	
	private FilaCobrancaController() {
		
	}
	
	public static void filaCobranca (Context ctx){
    	
		try {
			CobrancaForm cobrancaJson = ctx.bodyAsClass(CobrancaForm.class);
	    	CobrancaD cobranca = cobrancaJson.unserialize();
			FilaCobrancaController daoFilaCobrancaController = new FilaCobrancaController();	    	    	
	    	FilaCobranca filaCobranca = daoFilaCobrancaController.retornarFilaCobrancaPreenchida();
	    	filaCobranca.setQueueCobranca(cobranca);
	    	ctx.status(200);
	    	ctx.json(filaCobranca);
		}catch(Exception ex) {
			ctx.status(422);
		}
		
    }
    

  	public void deletarCobranca() {
  		FilaCobrancaController daoFilaController = new FilaCobrancaController();
  		FilaCobranca queue = daoFilaController.retornarFilaCobrancaPreenchida();
  		
  		CobrancaD cobrancaDeletada = queue.getQueueCobranca().remove();
  		Cobranca cobrar = new Cobranca();
  		
  		cobrar.setCobranca(cobrancaDeletada);
  		cobrar.sendCobranca();  		
  	}
  	
  	public FilaCobranca retornarFilaCobrancaPreenchida() {
  		FilaCobranca filaCobrancas = new FilaCobranca();
  		filaCobrancas.getQueueCobranca().add(new CobrancaD("3fa85f62-5717-4562-b3fc-2c963f66afa8", "2019-09-03T13:23:41.414Z", "2019-09-03T13:25:29.195Z", 10, "Pendente")) ;  		
  		return filaCobrancas;  		
  	}
  
  	public FilaCobranca getFilaCobranca() {
		return filaCobranca;
	}
}
