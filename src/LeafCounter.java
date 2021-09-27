import java.util.Scanner;

public class LeafCounter {
    private final double maxLeafStorageCapacity;

    private LeafCounter(double maxLeafStorageCapacityMeta){
        this.maxLeafStorageCapacity = maxLeafStorageCapacityMeta;
    }

    private double convertByMeta(double meta){
        return meta * Math.pow(10, 6);
    }

    private double countChargeLeafHours(double nowHyphaeStorageCapacityMeta,
                                        double nowHyphaeProductionPerHour){
        return convertByMeta(this.maxLeafStorageCapacity - nowHyphaeStorageCapacityMeta)
            / nowHyphaeProductionPerHour;
    }

    public static void main(String[] args) {
        double maxLeafStorageCapacityMeta = 6.5;
        double nowHyphaeProductionPerHour = 439000 - 78500;

        LeafCounter counter = new LeafCounter(maxLeafStorageCapacityMeta);

        while(true){
            Scanner scanner = new Scanner(System.in);
            System.out.print("輸入目前存量(M): ");
            double nowHyphaeStorageCapacityMeta = scanner.nextDouble();

            double chargeLeafHours = counter.countChargeLeafHours(nowHyphaeStorageCapacityMeta,
                nowHyphaeProductionPerHour);
            int hours = Integer.parseInt(Double.toString(chargeLeafHours).split("\\.")[0]);
            int minute = (int)((chargeLeafHours - hours) * 60);

            System.out.printf("添加 %02d:%02d 小時的葉子即可%n", hours, minute);
        }
    }
}
