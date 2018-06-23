import {Body, Controller, Post} from "@nestjs/common";
import {RaspeberryService} from "../raspberry/raspeberry.service";

@Controller()
export class MovilController {

    constructor(private _raspberryService:RaspeberryService){

    }
    @Post('añadirOrden')
    añadirOrden(@Body('tipo') tipo,@Body('raspberry') raspberry){
        return this._raspberryService.añadirOrden(raspberry,tipo);

    }

    @Post('ConsultarSenso')
    consultarSenso(@Body('raspberry') raspberry){
        return this._raspberryService.consultarSenso(raspberry);
    }

}