package agentes;

import redNeuronal.RedNeuronal;
import misionerosyCanibales.ArbolMisionero;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.util.leap.Serializable;

public class Agente1 extends Agent{
	
	protected void setup() {
		// TODO Auto-generated method stub
		System.out.println("Hola soy : "+ this.getName());
		
		Comportamiento comportamiento = new Comportamiento();
		comportamiento.setAgent(this);
		addBehaviour(comportamiento);
		
	}
	protected void takeDown() {
		// TODO Auto-generated method stub
		super.takeDown();
		System.out.println("No me mates: "+ this.getName());
	}
	
	class Comportamiento extends CyclicBehaviour{
		@Override
		public void action() {
			// TODO Auto-generated method stub
			ACLMessage mensaje = new ACLMessage(ACLMessage.INFORM);
			
			//agente1 sin Heuristica
			//ArbolMisionero misionerosyCanibales = new ArbolMisionero();
			//misionerosyCanibales.solucionarSinHeuristica();
			//int cantidadNodos = misionerosyCanibales.getContNodos();
			RedNeuronal red= new RedNeuronal();
			//red.prediccion();
			
			System.out.println("Prediccion co RED NEURONAAL...");
			enviarMensaje("Agente4", mensaje, red, "convIDAgente1-4" );
			getAgent().blockingReceive(1);
			
			getAgent().doDelete();
			
		}
		
		public void enviarMensaje(String receptor, ACLMessage mensaje, Serializable contenidoMsj, String conversationID){
			AID id = new AID();
			id.setLocalName(receptor);
			
			mensaje.setSender(getAgent().getAID());
			mensaje.addReceiver(id);
			//mensaje.setContent(contenidoMsj);
			try {
				mensaje.setContentObject(contenidoMsj);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			mensaje.setConversationId(conversationID);
			mensaje.setLanguage("Spanish");
			getAgent().send(mensaje);
		}
		
		
	}

}
