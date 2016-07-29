/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.tti.sefaz.signer;

import java.util.HashMap;

/**
 *
 * @author taragona
 */
public class PegaOpcoes {
    private Opcao[] permitidos;
    
    PegaOpcoes(Opcao[] permitidos) {
        this.permitidos = permitidos;
    }
    
    String help() {
        StringBuffer buf = new StringBuffer();
        
        for(Opcao op: permitidos) {
            buf.append(op.getDescricao()+"\n");
        }
        return buf.toString();
    }
    HashMap opcoes(String[] args) throws Exception {
        HashMap retorno = new HashMap();
        String chave = null;
        String parametro = null;
        boolean temArg = false;
        
        for(String arg: args) {
            if (arg.charAt(0) == '-') {
                if (temArg) {
                    throw new Exception("Erro:chave "+chave+" requer parametro!");
                }
                chave = null;
                for(Opcao opcao: permitidos) {
                    if (opcao.getId().equals(arg)) {
                        if (opcao.isHelp()) {
                            retorno = new HashMap();
                            retorno.put(arg,"-");
                            return retorno;
                        }
                        chave = arg;
                        if (opcao.isTemArg() == false) {
                            retorno.put(chave, "-");
                            temArg = false;
                        } else {
                            temArg = true;
                        }                        
                        break;
                    }
                }
                if (chave == null) {
                    throw new Exception("Erro:Chave "+arg+" invalida!");
                }
            } else {
                parametro = null;
                if (temArg) {
                    parametro = arg;
                    retorno.put(chave, parametro);
                    temArg = false;
                } else {
                    throw new Exception("Erro:chave "+chave+" requer parametro!");
                }
                parametro = arg;
            }
        }
        for(Opcao opcao: permitidos) {
            if (opcao.isObrigatorio()) {
                if (retorno.get(opcao.getId()) == null) {
                    throw new Exception("Erro:Parametro "+opcao.getId()+" obrigatorio!");
                }
            }
        }
        return retorno;
    }

}
