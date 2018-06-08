package agentes;

import javax.swing.JOptionPane;

import misionerosyCanibales.ArbolMisionero;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import agentes.Agente1.Comportamiento;

public class Agente3 extends Agent{
	
	protected void setup() {
		// TODO Auto-generated method stub
		System.out.println("Hola soy : "+ this.getName());
		
		Comportamiento comportamiento = new Comportamiento();
		comportamiento.setAgent(this);
		addBehaviour(comportamiento);
		
	}
	@Override
	protected void takeDown() {
		// TODO Auto-generated method stub
		super.takeDown();
		System.out.println("No me mates: "+ this.getName());
	}
	
	class Comportamiento extends CyclicBehaviour{
		@Override
		public void action() {
			// TODO Auto-generated method stub
			ACLMessage mensaje = getAgent().blockingReceive();
			String contenido = "";
			ArbolMisionero misionerosyCanibales = null;
			blockingReceive(1);
			
			if(mensaje.getConversationId().equals("convIDAgente4-3")){
				//contenido=mensaje.getContent();
				//System.out.println(contenido + " : " + mensaje.getConversationId());
				
				//System.out.println(mensaje.toString());
				System.out.println("Recibi mejor arbol");
				try {
					misionerosyCanibales = (ArbolMisionero) mensaje.getContentObject();
				} catch (Exception e) {
					// TODO: handle exception
				}
				mensaje = new ACLMessage( ACLMessage.INFORM);
				//ACLMessage respuesta = new ACLMessage( ACLMessage.INFORM);
				enviarMensaje("Agente4", mensaje, "Usuario informado de mejor camino Heuristico..!", "convIDAgente3");
				misionerosyCanibales.imprimirSolucionConHeur();
				//JOptionPane.showMessageDialog(null, misionerosyCanibales.imprimirSolucionConHeur());
				
				
			}else{
				
			}
			
		}
		
		
		
		public void enviarMensaje(String receptor, ACLMessage mensaje, String contenidoMsj, String conversationID){
			AID id = new AID();
			id.setLocalName(receptor);
			
			mensaje.setSender(getAgent().getAID());
			mensaje.addReceiver(id);
			mensaje.setContent(contenidoMsj);
			mensaje.setConversationId(conversationID);
			mensaje.setLanguage("Spanish");
			getAgent().send(mensaje);
		}
	}

}