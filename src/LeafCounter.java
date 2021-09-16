import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Scanner;

public class LeafCounter {
    private double maxLeafStorageCapacity;

    private LeafCounter(double maxLeafStorageCapacityMeta){
        this.maxLeafStorageCapacity = maxLeafStorageCapacityMeta * Math.pow(10, 6);
    }

    private double countChargeLeafHours(double nowHyphaeStorageCapacityMeta,
                                        double nowHyphaeProductionPerHour){
        return (this.maxLeafStorageCapacity - nowHyphaeStorageCapacityMeta)
            / nowHyphaeProductionPerHour;
    }

    private enum LeafCuttingAnts{
        L18(39000);

        private int hyphaeProductionPerHour;

        LeafCuttingAnts(int hyphaeProductionPerHour){
            this.hyphaeProductionPerHour = hyphaeProductionPerHour;
        }

        private int getHyphaeProductionPerHour(){
            return this.hyphaeProductionPerHour;
        }

    }

    public static void main(String[] args) {
        double maxLeafStorageCapacityMeta = 4.5;
        double nowHyphaeProductionPerHour =
            LeafCuttingAnts.L18.getHyphaeProductionPerHour() * 6;

        LeafCounter counter = new LeafCounter(maxLeafStorageCapacityMeta);

        Scanner scanner = new Scanner(System.in);
        System.out.print("輸入目前存量: ");
        double nowHyphaeStorageCapacityMeta = scanner.nextDouble();

        double chargeLeafHours = counter.countChargeLeafHours(nowHyphaeStorageCapacityMeta,
            nowHyphaeProductionPerHour) / 6;
        int hours = Integer.parseInt(Double.toString(chargeLeafHours).split("\\.")[0]);
        int minute = (int)((chargeLeafHours - hours) * 60);

        System.out.printf("添加 %02d:%02d 小時的葉子即可%n", hours, minute);
    }


}
