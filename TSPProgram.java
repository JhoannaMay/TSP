import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TSPProgram {

    public static int calculateTotalDistance(List<Integer> route, int[][] distanceMatrix) {
        int totalDistance = 0;
        for (int i = 0; i < route.size() - 1; i++) {
            totalDistance += distanceMatrix[route.get(i)][route.get(i + 1)];
        }
        // Add distance from last location back to the starting point
        totalDistance += distanceMatrix[route.get(route.size() - 1)][route.get(0)];
        return totalDistance;
    }

    public static List<Integer> tspBruteforce(int[][] distanceMatrix, List<String> locations) {
        List<Integer> optimalRoute = new ArrayList<>();
        List<Integer> currentRoute = new ArrayList<>();
        for (int i = 1; i < locations.size(); i++) { // Start with the second location (first location is starting point)
            currentRoute.add(i);
        }
        int minDistance = Integer.MAX_VALUE;

        do {
            List<Integer> tempRoute = new ArrayList<>(currentRoute);
            tempRoute.add(0, 0); // Add the starting point at the beginning
            tempRoute.add(0); // Add the starting point again at the end to return to the starting point

            int currentDistance = calculateTotalDistance(tempRoute, distanceMatrix);
            if (currentDistance < minDistance) {
                minDistance = currentDistance;
                optimalRoute = new ArrayList<>(tempRoute);
            }
        } while (nextPermutation(currentRoute));

        return optimalRoute;
    }

    public static boolean nextPermutation(List<Integer> array) {
        int i = array.size() - 2;
        while (i >= 0 && array.get(i) >= array.get(i + 1)) {
            i--;
        }
        if (i < 0) {
            return false;
        }

        int j = array.size() - 1;
        while (array.get(j) <= array.get(i)) {
            j--;
        }

        int temp = array.get(i);
        array.set(i, array.get(j));
        array.set(j, temp);

        reverse(array, i + 1, array.size() - 1);
        return true;
    }

    private static void reverse(List<Integer> array, int start, int end) {
        while (start < end) {
            int temp = array.get(start);
            array.set(start, array.get(end));
            array.set(end, temp);
            start++;
            end--;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of customers: ");
        int numCustomers = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        List<String> customerDeliveries = new ArrayList<>();
        for (int i = 0; i < numCustomers; i++) {
            System.out.print("Enter customer's name: ");
            String customerName = scanner.nextLine();
            System.out.print("Enter address (No, Street Name, Barangay, Municipality, Province): ");
            String address = scanner.nextLine();
            customerDeliveries.add(customerName + " - " + address);
        }

        List<String> locations = Arrays.asList("Lanao", "St. Peter", "St. John", "Maguindanao");

        int[][] distanceMatrix = {
                {0, 300, 150, 200},
                {150, 0, 200, 300},
                {100, 120, 0, 200},
                {200, 200, 100, 0}
        };

        System.out.println("Calculating smallest distance between the four points...");

        // Calculate the smallest distance without considering specific customer deliveries
        List<Integer> optimalRoute = tspBruteforce(distanceMatrix, locations);

        System.out.println("Smallest distance between the four points:");
        for (int locationIndex : optimalRoute) {
            System.out.println(locations.get(locationIndex));
        }

        int totalDistance = calculateTotalDistance(optimalRoute, distanceMatrix);
        System.out.printf("Total distance: %d units\n", totalDistance);

        scanner.close();
    }
}