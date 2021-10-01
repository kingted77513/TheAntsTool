public class EvolutionTimeCounter extends TimeCounter {
    @Override
    protected double get進化菌叢DiscountProportion() {
        return 0 / 100.0;
    }

    @Override
    protected double get聯盟進化DiscountProportion() {
        return 2 / 100.0;
    }

    public static void main(final String[] args) {
        System.out.println("進化升級時間計算");
        new EvolutionTimeCounter().run();
    }
}
