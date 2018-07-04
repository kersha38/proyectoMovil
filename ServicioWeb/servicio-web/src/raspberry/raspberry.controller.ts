import {    Body,
    Controller,
    FileInterceptor,
    Get,
    Post,
    Query,
    Req,
    Res,
    UploadedFile,
    UseInterceptors
} from "@nestjs/common";
import {RaspeberryService} from "./raspeberry.service";

// opciones del middleware multer para subir archivos
const multer = require('multer')
const storage = multer.diskStorage({
    destination: function(req, file, callback) {
        callback(null, './subidos')
    },
    filename: function(req, file, callback) {
        callback(null, file.originalname)
    }
})
@Controller('Raspberry')
export class RaspberryController {
    constructor(private _raspberryService:RaspeberryService){

    }

    @Get('actualizarSenso')
    actualizarSenso(@Query('raspberry') raspberry,@Query('estado') estado){
        return this._raspberryService.actualizarSenso(raspberry,estado);
    }

    @Get('obtenerOrden')
    obtenerOrden(@Query('raspberry') raspberry){
        return this._raspberryService.obtenerOrden(raspberry);
    }

    @Get('obtenerRaspberry')
    obtenerRaspberry(@Req() request,@Res() response){
        const raspberry=request.ip;
        console.log("idRaspberry: "+raspberry)
        response.redirect('http://api.qrserver.com/v1/create-qr-code/?data='+raspberry+'&size=100x100');
    }

    @Post('subirVideo')
    @UseInterceptors(FileInterceptor('file',
        {
            //dest:'subidos/',
            storage:storage
        }))
    uploadFile(@UploadedFile() file) {
        console.log("se subio un archivo".toLocaleUpperCase());
        console.log(file);
    }
}