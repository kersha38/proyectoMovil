package agentes;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.util.leap.Serializable;
import agentes.Agente1.Comportamiento;

public class Agente4 extends Agent{
	
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
		
		String contenidoAg1="";
		String contenidoAg2="";

		double[] prediccion=new double[1];
		double[] probabilidad=new double[2];
		
		@Override
		public void action() {
			// TODO Auto-generated method stub
			
			//bloqueado hasta q lleguen mensajes
			
			ACLMessage mensaje = getAgent().blockingReceive();
			
			if(mensaje.getConversationId().equals("convIDAgente1-4")){

				//contenidoAg1 = mensaje.getContent();
				//System.out.println(contenidoAg1 + " : " + mensaje.getConversationId());
				System.out.println(mensaje.toString());
			}
			
			if(prediccion[0]==0.0){
				System.out.println("La prediccion es: No se suscribe");
			}
			else if(prediccion[0]==1.0){
				System.out.println("La prediccion es: Si se suscribe");
			}
			//ACLMessage mensaje2 = getAgent().blockingReceive();
			
			if(mensaje.getConversationId().equals("convIDAgente2-4")){
				
			}
			//mensaje2.getConversationId().e
			/*if(misionerosyCanibalesConHeu != null && misionerosyCanibalesSinHeu != null){
				
				
				if(nodosConH < nodosSinH){
					System.out.println("Agente 2 : mejor !!");
					//envia info de nodos a Ag3
					blockingReceive(2);
					mensaje = new ACLMessage( ACLMessage.REQUEST);
					
					enviarMensaje("Agente3", mensaje, misionerosyCanibalesConHeu, "convIDAgente4-3"); 
					//getAgent().blockingReceive(2);
					esperarMensaje(mensaje);
				}else{
					
				}
				
				
			}*/
			
			
			
		}
		
		public void esperarMensaje(ACLMessage mensaje) {
			// TODO Auto-generated method stub
			mensaje = getAgent().blockingReceive();
			
			if(mensaje.getConversationId().equals("convIDAgente3")){
				//System.out.println(mensaje.getContent() + " : " + mensaje.getConversationId());
				System.out.println(mensaje.toString());
			}else{
				esperarMensaje(mensaje);
			}
			
			
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