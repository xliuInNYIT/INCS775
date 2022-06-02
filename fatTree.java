package INCS775;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class fatTree {
    public static void main(String[] args){
        boolean stopRunning  = false;
        while(!stopRunning) {
            stopRunning = generateFatTree();
        }
        // first of all, k pod means, k core switches
    }

    public static boolean generateFatTree() {
        // •Overall number of core switches: (k/2) *(k/2) = (k/2)^2
        // •Overall number of servers:  k *(k/2)* (k/2)= (k^3)/4
        // •Overall number of switches: k* k + (k/2)^2= ( k^2)*5/4
        // •Overall number of links : k* (k/2)* k + k* (k/2)^2 = (k^3)*3/4

        System.out.println("This is a fatTree Generation program");
        System.out.println("Please enter n parameter, which represents the k's pod, and it must be times of 4");
        Scanner myObj = new Scanner(System.in);
        int k = 0;
        try{
            k = myObj.nextInt();
            if(k == 0 || k % 4 != 0){
                return false;
            }

            if(k <= 0){
                return true;
            }
        } catch (Exception e){
            return false;
        }

        int[] index = new int[1];
        // all result will be stored in the result array
        List<String> results = new ArrayList<String>();
        // populate the servers
        int serverLastIndex = (k * k * k)/4 - 1;
        // populate the pods
        int firstLayerPodLastIndex =  serverLastIndex + (k * k)/2;
        int secondLayerPodLastIndex = serverLastIndex + (k * k);

        // populate the cores
        int coreLastIndex = secondLayerPodLastIndex + k * k / 4;

        System.out.println("serverLastIndex : " + serverLastIndex);
        System.out.println("firstLayerPodLastIndex : " + firstLayerPodLastIndex);
        System.out.println("secondLayerPodLastIndex : " + secondLayerPodLastIndex);
        System.out.println("coreLastIndex : " + coreLastIndex);
        int totalMachineCount = coreLastIndex + 1;
        boolean[][] result = new boolean[totalMachineCount][totalMachineCount];

        // connection the first servers to the first port.
        // and the first port layer is having switches k
        // then each switches will connect to k/4 server.
        // so the connection for the first server will be

        int serverIndex = 0;
        for(int i = serverLastIndex + 1; i <= firstLayerPodLastIndex; i ++){
            for(int j = serverIndex; j <serverIndex + k/2; j ++){
                System.out.println("connecting " + i + "  " + j);
                result[i][j] = true;
                result[j][i] = true;
            }

            serverIndex += k /2 ;
        }
        //printResult(result);

        // connection for Pod first layer and second layer
        // we need to consider pot by pot
        // so each port has k switches(total amount is k*k, so each pot has k*k/k = k)
        // then each port in each layer has k/2 switches

        // init first layer switch
        int firstLayerSwitchIndex = serverLastIndex + 1;
        for(int m = 0; m < k; m ++){
            int secondlayerSwitchIndex = firstLayerSwitchIndex + (k * k / 2);
            for(int n = firstLayerSwitchIndex; n < firstLayerSwitchIndex + k/2; n ++){
                for(int o = secondlayerSwitchIndex; o < secondlayerSwitchIndex + k/2; o ++){
                    result[n][o] = true;
                    result[o][n] = true;
                }
            }
            // pod connection completed, update first layer Switch Index
            firstLayerSwitchIndex += k/2;
        }

        // printResult(result);
        // connection for port second layer and the core
        // still we need to consider it port by port
        // we have total core (k/2)^2,     then for each port it is k/2 core switches
        // as the same as the last step, each port second layer has k/2 aggr switches
        // first k/2 switches will connected to first port

        int coreIndex  = secondLayerPodLastIndex + 1;
        int aggrIndex = firstLayerPodLastIndex + 1;
        int offSet = 0;
        for(int m = 0; m < k; m ++){
            for(int n = aggrIndex; n < aggrIndex + k/2; n ++){
                int coreCurr = coreIndex;
                while(coreCurr < coreIndex + k/2){
                    result[coreCurr][n] = true;
                    result[n][coreCurr] = true;
                    coreCurr ++;
                }

                offSet = (offSet + k/2) % (k * k / 4);
                coreIndex = secondLayerPodLastIndex + 1 + offSet ;
            }

            aggrIndex += k/2;
        }

        printResult(result);
        return false;
    }

    public static void printResult(boolean[][] result){
        int countConnect = 0;
        for(int i = 0; i < result.length; i ++){
            for(int j = 0; j < i; j ++){
                String connectValue = result[i][j]? "9999": "1";
                System.out.println("N" + i + "  N" + j + "   " +  connectValue);

                if(result[i][j]){
                    countConnect ++;
                }
            }
        }

        System.out.println(countConnect);
    }
}



// PriorityQueue<Entry> pq = new PriorityQueue<Entry>((a, b) -> Integer.compare(lists.get(a.listId).get(a.index), lists.get(b.listId).get(b.index)));
