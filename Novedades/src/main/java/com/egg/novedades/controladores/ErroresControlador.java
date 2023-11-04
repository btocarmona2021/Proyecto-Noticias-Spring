package com.egg.novedades.controladores;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErroresControlador implements ErrorController {

            @RequestMapping(value = "/error",method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView renderizaPaginaError(HttpServletRequest request){
                ModelAndView errorPage = new ModelAndView("error");
                String mensajeDeError= "";
                int httpErrorCode = obtenerCpdigoDeError(request);
                System.out.println( httpErrorCode + " este es el codigo de error que lanzo");
                switch (httpErrorCode){
                    case 400:{


                    }
                    case 401:{

                    }
                    case 403:{

                    }
                    case 404:{
                        mensajeDeError= "No se pudo encontrar la pagina";
                        break;
                    }
                    case 500:{
                        mensajeDeError= "Error en el servidor";
                        break;
                    }
                }
                errorPage.addObject("codigo",httpErrorCode);
                errorPage.addObject("mensaje",mensajeDeError);
                return  errorPage;
            }

            private int obtenerCpdigoDeError(HttpServletRequest request){
                int codigoError = (int) request.getAttribute("javax.servlet.error.status_code");
                return codigoError;
            }
            public String getErrorPath(){
                return "/error.html";
            }
}
