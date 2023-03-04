import java.util.Scanner;

public class Driver
{
    public static void main(String[] args)
    {
        BigInt firstBigInt;
        BigInt secondBigInt;
        BigInt addedBigInt;
        BigInt subtractedBigInt;
        BigInt multipliedBigInt;
        BigInt dividedBigInt;
        try {

            firstBigInt = new BigInt("+4324242424242424324242421243234234242424");
            secondBigInt = new BigInt("-34242423342423432432432424");

            addedBigInt = firstBigInt.add(secondBigInt);
            System.out.println("Your numbers added: "+addedBigInt.toString());


            subtractedBigInt = firstBigInt.subtract(secondBigInt);
            System.out.println("Your numbers subtracted: "+subtractedBigInt.toString());

            multipliedBigInt = firstBigInt.multiply(secondBigInt);
            System.out.println("Your numbers multiplied: "+multipliedBigInt.toString());

            dividedBigInt = firstBigInt.divide(secondBigInt);
            System.out.println("Your numbers divided: "+dividedBigInt.toString());


        } catch (BigIntException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
        }





    }
}