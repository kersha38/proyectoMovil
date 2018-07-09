import {Controller, Get, Query} from "@nestjs/common";
import {RaspeberryService} from "../raspberry/raspeberry.service";

@Controller('Movil')
export class MovilController {

    constructor(private _raspberryService:RaspeberryService){

    }
    @Get('anadirOrden')
    añadirOrden(@Query('tipo') tipo,@Query('raspberry') raspberry){
        return this._raspberryService.añadirOrden(raspberry,tipo);

    }

    @Get('consultarSenso')
    consultarSenso(@Query('raspberry') raspberry){
        return this._raspberryService.consultarSenso(raspberry);
    }

}