public abstract class Mens {

    protected Vakje vakje;
    protected Vakje destinatie;

    protected int leeftijd = 0;
    protected int maxLeeftijd = 300; // voorbeeld

    public Mens(Vakje start) {
        this.vakje = start;
        start.zetMens(this);
    }

    public abstract void beweeg();
}