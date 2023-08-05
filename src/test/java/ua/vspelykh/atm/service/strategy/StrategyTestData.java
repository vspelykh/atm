package ua.vspelykh.atm.service.strategy;

import ua.vspelykh.atm.model.dto.BanknoteDTO;
import ua.vspelykh.atm.model.entity.Banknote;

import java.util.List;

public class StrategyTestData {

    static final int ATM_ID = 1;

    static Banknote B10_FULL = new Banknote(1, 10, 10);
    static Banknote B20_FULL = new Banknote(1, 20, 10);
    static Banknote B50_FULL = new Banknote(1, 50, 10);
    static Banknote B100_FULL = new Banknote(1, 100, 10);
    static Banknote B200_FULL = new Banknote(1, 200, 10);
    static Banknote B500_FULL = new Banknote(1, 500, 10);

    static Banknote B10_EMPTY = new Banknote(1, 10, 0);
    static Banknote B20_EMPTY = new Banknote(1, 20, 0);
    static Banknote B50_EMPTY = new Banknote(1, 50, 0);
    static Banknote B100_EMPTY = new Banknote(1, 100, 0);
    static Banknote B200_EMPTY = new Banknote(1, 200, 0);
    static Banknote B500_EMPTY = new Banknote(1, 500, 0);

    static Banknote B10_ONE = new Banknote(1, 10, 1);
    static Banknote B20_ONE = new Banknote(1, 20, 1);
    static Banknote B50_ONE = new Banknote(1, 50, 1);
    static Banknote B100_ONE = new Banknote(1, 100, 1);
    static Banknote B200_ONE = new Banknote(1, 200, 1);
    static Banknote B500_ONE = new Banknote(1, 500, 1);

    static Banknote B50_LOW = new Banknote(1, 50, 4);


    static List<Banknote> fullBanknotes(){
        Banknote B10_FULL = new Banknote(1, 10, 10);
        Banknote B20_FULL = new Banknote(1, 20, 10);
        Banknote B50_FULL = new Banknote(1, 50, 10);
        Banknote B100_FULL = new Banknote(1, 100, 10);
        Banknote B200_FULL = new Banknote(1, 200, 10);
        Banknote B500_FULL = new Banknote(1, 500, 10);

        return List.of(B10_FULL, B20_FULL, B50_FULL, B100_FULL, B200_FULL, B500_FULL);
    }

    static List<Banknote> empty50s(){
        return List.of(B10_FULL, B20_FULL, B50_EMPTY, B100_FULL, B200_FULL, B500_FULL);
    }
    static List<Banknote> low50s(){
        Banknote B10_FULL = new Banknote(1, 10, 10);
        Banknote B20_FULL = new Banknote(1, 20, 10);
        return List.of(B10_FULL, B20_FULL, B50_LOW, B100_FULL, B200_FULL, B500_FULL);
    }

    static List<Banknote> lowSmallBanknotes(){
        return List.of(B10_EMPTY, B20_ONE, B50_ONE, B100_FULL, B200_FULL, B500_FULL);
    }

    static List<Banknote> lowSmallBanknotes2(){
        return List.of(B10_ONE, B20_ONE, B50_ONE, B100_FULL, B200_FULL, B500_FULL);
    }

    static List<Banknote> lowSmallBanknotes3(){
        return List.of(B10_ONE, B20_FULL, B50_ONE, B100_FULL, B200_FULL, B500_FULL);
    }

    static List<Banknote> smallStrategyRes1(){
         Banknote B10 = new Banknote(1, 10, 0);
         Banknote B20 = new Banknote(1, 20, 8);
         Banknote B50 = new Banknote(1, 50, 0);
         Banknote B100 = new Banknote(1, 100, 10);
         Banknote B200 = new Banknote(1, 200, 10);
         Banknote B500 = new Banknote(1, 500, 10);
        return List.of(B10, B20, B50, B100, B200, B500);
    }

    static List<Banknote> smallStrategyRes2(){
        Banknote B10 = new Banknote(1, 10, 9);
        Banknote B20 = new Banknote(1, 20, 8);
        Banknote B50 = new Banknote(1, 50, 3);
        Banknote B100 = new Banknote(1, 100, 10);
        Banknote B200 = new Banknote(1, 200, 10);
        Banknote B500 = new Banknote(1, 500, 10);
        return List.of(B10, B20, B50, B100, B200, B500);
    }

    static List<Banknote> smallStrategyRes3(){
        Banknote B10 = new Banknote(1, 10, 8);
        Banknote B20 = new Banknote(1, 20, 1);
        Banknote B50 = new Banknote(1, 50, 0);
        Banknote B100 = new Banknote(1, 100, 10);
        Banknote B200 = new Banknote(1, 200, 10);
        Banknote B500 = new Banknote(1, 500, 10);
        return List.of(B10, B20, B50, B100, B200, B500);
    }

    static List<BanknoteDTO> resDTO1(){
        BanknoteDTO B10 = new BanknoteDTO(1, 10, 1);
        BanknoteDTO B20 = new BanknoteDTO(1, 20, 2);
        BanknoteDTO B50 = new BanknoteDTO(1, 50, 1);

        return List.of(B10, B20, B50);
    }

    static List<BanknoteDTO> resDTO2(){
        BanknoteDTO B10 = new BanknoteDTO(1, 10, 1);
        BanknoteDTO B20 = new BanknoteDTO(1, 20, 2);
        BanknoteDTO B50 = new BanknoteDTO(1, 50, 7);

        return List.of(B10, B20, B50);
    }

    static List<BanknoteDTO> resDTO3(){
        BanknoteDTO B10 = new BanknoteDTO(1, 10, 2);
        BanknoteDTO B20 = new BanknoteDTO(1, 20, 9);
        BanknoteDTO B50 = new BanknoteDTO(1, 50, 4);

        return List.of(B10, B20, B50);
    }
}
