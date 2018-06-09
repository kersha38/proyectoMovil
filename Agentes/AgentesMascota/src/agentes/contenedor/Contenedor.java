package agentes.contenedor;

import agentes.Agente1;
import agentes.Agente2;
import agentes.Agente3;
import agentes.Agente4;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;


public class Contenedor {
	
	AgentContainer agenteContainer;
	AgentController agenteController;
	
	public void crearContenedor() {
		// TODO Auto-generated method stub
		jade.core.Runtime runtime = jade.core.Runtime.instance();
		runtime.setCloseVM(true);
		
		//perfil
		Profile profile = new ProfileImpl(null, 1007, null);
		agenteContainer = runtime.createMainContainer(profile);
	}

	public void crearAgentes() {
		// TODO Auto-generated method stub
		
		try {
			agenteController = agenteContainer.createNewAgent("Agente4", Agente4.class.getName(), null);
			agenteController.start();
			agenteController = agenteContainer.createNewAgent("Agente3", Agente3.class.getName(), null);
			agenteController.start();
			agenteController = agenteContainer.createNewAgent("Agente2", Agente2.class.getName(), null);
			agenteController.start();
			agenteController = agenteContainer.createNewAgent("Agente1", Agente1.class.getName(), null);
			agenteController.start();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

}
