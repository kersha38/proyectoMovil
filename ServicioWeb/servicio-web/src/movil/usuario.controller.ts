import {Body, Controller, Get, Post} from "@nestjs/common";
import {UsuarioService} from "./usuario.service";
import {RaspeberryService} from "../raspberry/raspeberry.service";

@Controller('Usuario')
export class UsuarioController {
    constructor(private _usuarioService: UsuarioService,
                _raspberryService: RaspeberryService) {
        _usuarioService.listarUsuarios();
    }

    @Post('autentificarComun')
    autentificarComun(@Body('nickname') nickname,@Body('password') password){
        return this._usuarioService.autentificarComun(nickname,password);
    }

    @Post('autentificarGmailFb')
    autentificarGmailFb(@Body('mail') mail){
        return this._usuarioService.autentificarGmailFb(mail);
    }

    @Post()
    existeGmailFb(@Body('mail') mail){
        return this._usuarioService.existeGmailFb(mail);
    }

    @Post('crearComun')
    crearComun(
        @Body('nickname') nickname,
        @Body('mail') mail,
        @Body('password') password
    ) {
        return this._usuarioService.crearUsuarioComun(nickname, mail, password);
    }

    @Post('crearConGmailFb')
    crearConGmailFb(
        @Body('nickname') nickname,
        @Body('mail') mail,
    ) {
        return this._usuarioService.crearConGmailFb(nickname, mail);
    }

    @Get('listar')
    listar() {
        return this._usuarioService.listarUsuarios();
    }

    @Post('obtener')
    obtenerUsuario(@Body('nickname') nickname) {
        return this._usuarioService.obtenerUsuario(nickname);
    }
}