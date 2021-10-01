import java.util.Scanner;

public class LeafCounter {
    private final double maxLeafStorageCapacity;

    private LeafCounter(final double maxLeafStorageCapacityMeta) {
        this.maxLeafStorageCapacity = maxLeafStorageCapacityMeta;
    }

    private double convertByMeta(final double meta) {
        return meta * Math.pow(10, 6);
    }

    private double countChargeLeafHours(final double nowHyphaeStorageCapacityMeta,
                                        final double nowHyphaeProductionPerHour) {
        return convertByMeta(this.maxLeafStorageCapacity - nowHyphaeStorageCapacityMeta)
            / nowHyphaeProductionPerHour;
    }

    public static void main(final String[] args) {
        final double maxLeafStorageCapacityMeta = 7.5;
        final double nowHyphaeProductionPerHour = 439000 - 78800;

        final LeafCounter counter = new LeafCounter(maxLeafStorageCapacityMeta);

        while (true) {
            final Scanner scanner = new Scanner(System.in);
            System.out.print("輸入目前存量(M): ");
            final double nowHyphaeStorageCapacityMeta = scanner.nextDouble();

            final double chargeLeafHours = counter.countChargeLeafHours(nowHyphaeStorageCapacityMeta,
                nowHyphaeProductionPerHour);
            final int hours = Integer.parseInt(Double.toString(chargeLeafHours).split("\\.")[0]);
            final int minute = (int) ((chargeLeafHours - hours) * 60);

            System.out.printf("添加 %02d:%02d 小時的葉子即可%n", hours, minute);
        }
    }
}
