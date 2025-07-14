package it.unipv.ingsw.BSSTansport.StatusMonitor.infrastructure.beans;

public class CheckpointSuLineaBean implements Comparable<CheckpointSuLineaBean> {
    private int checkpoint;
    private int numero;
    private int delayS;
    private int tipo;
    private Integer lunghezza;
    private Integer durataFermataS;

    public CheckpointSuLineaBean(int checkpoint, int numero, int delayS, int tipo, int lunghezza, int durataFermataS) {
        this.checkpoint = checkpoint;
        this.numero = numero;
        this.delayS = delayS;
        this.tipo = tipo;
        this.lunghezza = lunghezza;
        this.durataFermataS = durataFermataS;
    }

    public int compareTo(CheckpointSuLineaBean checkpointSuLineaBean) {
        return this.numero - checkpointSuLineaBean.getNumero();
    }


    public Integer getDurataFermataS() {
        return durataFermataS;
    }

    public Integer getLunghezza() {
        return lunghezza;
    }

    public int getTipo() {
        return tipo;
    }

    public int getDelayS() {
        return delayS;
    }

    public void setDelayS(int delayS) {
        this.delayS = delayS;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getCheckpoint() {
        return checkpoint;
    }
}
