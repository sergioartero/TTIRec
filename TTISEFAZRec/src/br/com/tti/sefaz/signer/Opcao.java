/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.tti.sefaz.signer;

/**
 *
 * @author taragona
 */
public class Opcao {
    
    private String id = null;
    private boolean temArg = false;
    private boolean obrigatorio = false;
    private String descricao = null;
    private boolean help = false;
    
    Opcao(String id, boolean temArg, boolean obrigatorio, boolean help, String descricao) {
        this.id = id;
        this.temArg = temArg;
        this.obrigatorio = obrigatorio;
        this.descricao = descricao;
        this.help = help;
    }

    public boolean isHelp() {
        return help;
    }

    
    public String getId() {
        return id;
    }

    public boolean isObrigatorio() {
        return obrigatorio;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean isTemArg() {
        return temArg;
    }
    
    
}
