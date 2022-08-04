public class PhaserDiagram {
    private Phaser phaserA;
    private Phaser phaserB;
    private Phaser phaserC;

    public PhaserDiagram(Phaser phaserA, Phaser phaserB, Phaser phaserC) {
        this.phaserA = phaserA;
        this.phaserB = phaserB;
        this.phaserC = phaserC;
    }

    public Phaser getPhaserA() {
        return phaserA;
    }

    public void setPhaserA(Phaser phaserA) {
        this.phaserA = phaserA;
    }

    public Phaser getPhaserB() {
        return phaserB;
    }

    public void setPhaserB(Phaser phaserB) {
        this.phaserB = phaserB;
    }

    public Phaser getPhaserC() {
        return phaserC;
    }

    public void setPhaserC(Phaser phaserC) {
        this.phaserC = phaserC;
    }

    public void setSelected(boolean b)
    {
        phaserA.setSelected(false);
        phaserB.setSelected(false);
        phaserC.setSelected(false);
    }

}
