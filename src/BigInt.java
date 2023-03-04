import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BigInt implements Comparable<BigInt>
{
    /**
     * Constructor name: BigInt
     * @param pStringNumber (String)
     * @throws BigIntException
     *
     *
     */
    final boolean sign;
    final byte[] numberArray;
    public BigInt(String pStringNumber) throws BigIntException
    {

        Matcher patternMatcher;
        pStringNumber = pStringNumber.replaceAll(" ", "");


        String patternInput = "^[-+][0-9]+";
        Pattern patternReader = Pattern.compile(patternInput);
        patternMatcher = patternReader.matcher(pStringNumber);
        if (patternMatcher.matches() == false)
        {
            throw new BigIntException("Not a valid digit");
        }

        if (pStringNumber.charAt(0) == '+')
        {
            pStringNumber = pStringNumber.substring(1);
            sign = true;
        }
        else if (pStringNumber.charAt(0) == '-')
        {
            pStringNumber = pStringNumber.substring(1);
            sign = false;
        }
        else
        {
            sign = true;
        }

        numberArray = stringToArray(pStringNumber);
    }

    /**
     * Constructor name: BigInt
     * @param bigIntArray (byte[])
     * @param sign (boolean)
     *
     * Inside the constructor:
     *  1.
     */
    public BigInt(byte[] bigIntArray, boolean sign)
    {
        this.numberArray = bigIntArray;
        this.sign = sign;
    }

    private class QuotientRemainder
    {
        public byte[] quotient;
        public byte[] remainder;
    }



    /**
     * @param pBigInt
     * @return int
     */
    @Override
    public int compareTo(BigInt pBigInt)
    {
        int comparisonVal = 0;
        int index = 0;


        if (this.numberArray.length > pBigInt.numberArray.length)
        {
            comparisonVal = 1;
        }

        if (this.numberArray.length < pBigInt.numberArray.length)
        {
            comparisonVal = -1;
        }

        if (this.numberArray.length == pBigInt.numberArray.length)
        {
            for (index = 0; index < this.numberArray.length; index++)
            {
                if (this.numberArray[index] > pBigInt.numberArray[index])
                {
                    comparisonVal = 1;
                    break;
                }
                else if((this.numberArray[index] < pBigInt.numberArray[index])) {
                    comparisonVal = -1;
                    break;
                }
            }
        }

        return comparisonVal;
    }

    /**
     *
     * @param pBigInt
     * @return
     */
    public int compareAbsoluteValues(BigInt pBigInt)
    {
        byte[] firstArray = Arrays.copyOf(this.numberArray, this.numberArray.length);
        byte[] secondArray = Arrays.copyOf(pBigInt.numberArray, pBigInt.numberArray.length);
        int comparisonVal = 0;
        int index = 0;

        //Remove leading zeroes
        firstArray = removeLeadingZeroes(firstArray);
        secondArray = removeLeadingZeroes(secondArray);

        if (firstArray.length > secondArray.length)
        {
            comparisonVal = 1;
        }

        if (firstArray.length < secondArray.length)
        {
            comparisonVal = -1;
        }

        if (firstArray.length == secondArray.length)
        {
            for (index = 0; index < firstArray.length; index++)
            {
                if (firstArray[index] > secondArray[index])
                {
                    comparisonVal = 1;
                    break;
                }
                else if((firstArray[index] < secondArray[index])) {
                    comparisonVal = -1;
                    break;
                }
            }
        }

        return comparisonVal;
    }


    /**
     * @return String
     */
    @Override
    public String toString()
    {
        int index = 0;
        String returnedString = "";
        String returnedSign = "";



        for (index = 0; index < this.numberArray.length; index++)
        {
            returnedString += this.numberArray[index];
        }

        if (this.sign == false)
        {
            returnedSign = "-";
        }
        else if (this.sign == true)
        {
            returnedSign = "+";
        }


        if (this.numberArray.length == 0) // If two numbers were added and subtracted and returned a 0, e.g., +200 + -200
        {
            returnedSign = "";
            returnedString = "0";
        }

        return returnedSign+returnedString;

    }



    /**
     * @param inputString
     * @return byte[]
     */
    public byte[] stringToArray(String inputString)
    {
        int index = 0;
        byte[] newArray = new byte[inputString.length()];
        for (index = 0; index < inputString.length(); index++)
        {
            // - '0' ensures the digit can be displayed as the desired integer and not an ascii value.
            newArray[index] = (byte)(inputString.charAt(index) - '0');
        }

        return newArray;
    }


    /**
     * @param inputArray
     * @return String
     */
    public String arrayToString(byte[] inputArray)
    {
        String stringOfArray = "";
        int index = 0;
        for (index = 0; index < inputArray.length; index++)
        {
            stringOfArray += inputArray[index];
        }

        return stringOfArray;
    }


    /**
     *
     * @param pBigArray (byte[])
     * @param pSmallArray (byte[])
     * @return (byte[])
     */
    public byte[] addLeadingZeroes(byte[] pBigArray, byte[] pSmallArray)
    {
        int index = 0;
        byte[] arrayWithLeadingZeroes = new byte[pBigArray.length]; // The size of the new array has to be the size of the bigger array
        int leadingZeroesIndex = pBigArray.length - pSmallArray.length; // Index will be used to see where to start in new array (unfilled elements will be zero)
        for (index = 0; index < pSmallArray.length; index++)
        {
            arrayWithLeadingZeroes[leadingZeroesIndex] = pSmallArray[index];
            leadingZeroesIndex++;
        }

        return arrayWithLeadingZeroes;
    }


    /**
     *
     * @param pArray
     * @return
     */
    public byte[] removeLeadingZeroes(byte[] pArray)
    {
        int index = 0;
        int leadingZeroIndex = 0; // Will ensure where to cut the zeroes from
        for (index = 0; index < pArray.length; index++ ) // for-loop counts how many leading-zeroes there are
        {
            if (pArray[index] != 0)
            {
                break;
            }
            else if(pArray[index] == 0)
            {
                leadingZeroIndex++;
            }
        }

        // The new size of the new array has to be subracted by the amount of zeroes removed
        byte[] arrayWithNoLeadingZeroes = new byte[pArray.length - leadingZeroIndex];
        for (index = 0; index < arrayWithNoLeadingZeroes.length; index++ ) // for-loop uses the info from the previous loop to create a new loop without leadingzeroes
        {
            arrayWithNoLeadingZeroes[index] = pArray[leadingZeroIndex];
            leadingZeroIndex++;
        }


        return arrayWithNoLeadingZeroes;

    }



    /**
     * @param pSecondNumber
     * @return BigInt
     */
    public BigInt add(BigInt pSecondNumber)
    {
        BigInt sumBigInt = null;

        // If both signs are positive, add numbers and make sign positive
        if (this.sign == true && pSecondNumber.sign == true)
        {
            sumBigInt = this.privAdd(pSecondNumber);
            sumBigInt = new BigInt(sumBigInt.numberArray, true);

        }

        // If both signs are negative, add and make sign negative
        else if (this.sign == false && pSecondNumber.sign == false)
        {
            sumBigInt = this.privAdd(pSecondNumber);
            sumBigInt = new BigInt(sumBigInt.numberArray, false);

        }


        // If the the first number is has a bigger value
        else if (this.compareAbsoluteValues(pSecondNumber) > 0)
        {
            if (this.sign == true && pSecondNumber.sign == false)
            {
                sumBigInt = this.privSubtract(pSecondNumber);
                sumBigInt = new BigInt(sumBigInt.numberArray, true);
            }

            if (this.sign == false && pSecondNumber.sign == true)
            {
                sumBigInt = this.privSubtract(pSecondNumber);
                sumBigInt = new BigInt(sumBigInt.numberArray, false);
            }

        }

        // If the second number has a bigger value
        else if (this.compareAbsoluteValues(pSecondNumber) < 0)
        {
            if (this.sign == true && pSecondNumber.sign == false)
            {
                sumBigInt = pSecondNumber.privSubtract(this);
                sumBigInt = new BigInt(sumBigInt.numberArray, false);
            }

            if (this.sign == false && pSecondNumber.sign == true)
            {
                sumBigInt = pSecondNumber.privSubtract(this);
                sumBigInt = new BigInt(sumBigInt.numberArray, true);
            }
        }

        else if (this.compareAbsoluteValues(pSecondNumber) == 0)
        {
            if (this.sign != pSecondNumber.sign)
            {
                sumBigInt = this.privSubtract(pSecondNumber);
                sumBigInt = new BigInt(sumBigInt.numberArray, true);
            }

            else if (this.sign == true && pSecondNumber.sign == true)
            {
                sumBigInt = this.privAdd(pSecondNumber);
                sumBigInt = new BigInt(sumBigInt.numberArray, true);
            }

            else if (this.sign == false && pSecondNumber.sign == false)
            {
                sumBigInt = this.privAdd(pSecondNumber);
                sumBigInt = new BigInt(numberArray, false);
            }

        }


        return sumBigInt;
    }


    /**
     *
     * @param pSecondNumber
     * @return
     */
    private BigInt privAdd(BigInt pSecondNumber)
    {
        BigInt sumBigInt = null;
        int carry = 0;
        int sizeOfAddedArray = 0;
        int index = 0;
        byte[] addedArray;
        byte[] firstNumArray = Arrays.copyOf(this.numberArray, this.numberArray.length);
        byte[] secondNumArray = Arrays.copyOf(pSecondNumber.numberArray, pSecondNumber.numberArray.length);
        byte[] greaterArray;
        byte[] smallerArray;

        // If-else statement ensures that the smaller number is given leading zeroes to make the algorithm easier
        if (firstNumArray.length > secondNumArray.length)
        {
            greaterArray = Arrays.copyOf(firstNumArray, firstNumArray.length);
            smallerArray = Arrays.copyOf(secondNumArray, secondNumArray.length);
            sizeOfAddedArray = firstNumArray.length; // The size of the new array has to be at least the size of the biggest array
            smallerArray = addLeadingZeroes(firstNumArray, secondNumArray); // add leading zeroes to the smaller array
        }
        else if (firstNumArray.length < secondNumArray.length)
        {
            greaterArray = Arrays.copyOf(secondNumArray, secondNumArray.length);
            smallerArray = Arrays.copyOf(firstNumArray, firstNumArray.length);
            sizeOfAddedArray = secondNumArray.length; // The size of the new array has to be at least the size of the biggest array
            smallerArray = addLeadingZeroes(secondNumArray, firstNumArray);
        }
        else
        {
            greaterArray = Arrays.copyOf(firstNumArray, firstNumArray.length);
            smallerArray = Arrays.copyOf(secondNumArray, secondNumArray.length);
            sizeOfAddedArray = firstNumArray.length; // If both arrays are equal, the new array can be the size of either
        }
        addedArray = new byte[sizeOfAddedArray + 1]; // If two numbers are added, the new digit can only be 1 digit longer than the previous biggest digit


        for (index = greaterArray.length-1; index >= 0; index--)
        {
            if (greaterArray[index] + smallerArray[index] > 10) // If the two numbers added are over 10, it carries it over onto the next operation
            {
                addedArray[index+1] = (byte)(greaterArray[index] + smallerArray[index] - 10);
                carry = 1;
            }
            else
            {
                addedArray[index+1] = (byte)(greaterArray[index] + carry + smallerArray[index]); // Uses the carry variable from the previous if-statement
                carry = 0;
            }
        }

        addedArray = removeLeadingZeroes(addedArray); // Removes any leading zeroes that are left after the operation
        sumBigInt = new BigInt(addedArray, true); // Constructs the newly added array.

        return sumBigInt;

    }



    /**
     * @param pSecondNumber
     * @return BigInt
     */
    public BigInt subtract(BigInt pSecondNumber)
    {
        BigInt subtractedBigInt = null;

        // If the first number is bigger (After absolute value)
        if (this.compareAbsoluteValues(pSecondNumber) > 0)
        {
            if (this.sign == true && pSecondNumber.sign == true) // returns a positive
            {
                subtractedBigInt = this.privSubtract(pSecondNumber);
                subtractedBigInt = new BigInt(subtractedBigInt.numberArray, true);
            }

            else if (this.sign == false && pSecondNumber.sign == false)
            {
                subtractedBigInt = this.privSubtract(pSecondNumber);
                subtractedBigInt = new BigInt(subtractedBigInt.numberArray, false);
            }

            else if (this.sign == true && pSecondNumber.sign == false) // returns a positive
            {
                subtractedBigInt = this.privAdd(pSecondNumber);
                subtractedBigInt = new BigInt(subtractedBigInt.numberArray, true);
            }

            else if (this.sign == false && pSecondNumber.sign == true) // returns negative, two negatives
            {
                subtractedBigInt = this.privAdd(pSecondNumber);
                subtractedBigInt = new BigInt(subtractedBigInt.numberArray, false);
            }



        }


        // If the second number is bigger (After absolute value)
        else if (this.compareAbsoluteValues(pSecondNumber) < 0)
        {
            if (this.sign == true && pSecondNumber.sign == true) // returns negative
            {
                subtractedBigInt = pSecondNumber.privSubtract(this);
                subtractedBigInt = new BigInt(subtractedBigInt.numberArray, false);
            }

            else if (this.sign == false && pSecondNumber.sign == false) // returns positive
            {
                subtractedBigInt = pSecondNumber.privSubtract(this);
                subtractedBigInt = new BigInt(subtractedBigInt.numberArray, true);

            }

            else if (this.sign == true && pSecondNumber.sign == false) // returns positive
            {
                subtractedBigInt = pSecondNumber.privAdd(this);
                subtractedBigInt = new BigInt(subtractedBigInt.numberArray, true);
            }

            else if (this.sign == false && pSecondNumber.sign == true) // returns negative
            {
                subtractedBigInt = pSecondNumber.privAdd(this);
                subtractedBigInt = new BigInt(subtractedBigInt.numberArray, false);
            }


        }

        // If both numbers are equal (After absolute value)
        else if (this.compareAbsoluteValues(pSecondNumber) == 0)
        {
            if (this.sign == true && pSecondNumber.sign == true) // returns 0
            {
                subtractedBigInt = this.privSubtract(pSecondNumber);
                subtractedBigInt = new BigInt(subtractedBigInt.numberArray, true);
            }

            else if (this.sign == false && pSecondNumber.sign == false) // returns 0
            {
                subtractedBigInt = this.privSubtract(pSecondNumber);
                subtractedBigInt = new BigInt(subtractedBigInt.numberArray, true);
            }

            else if (this.sign == true && pSecondNumber.sign == false) // returns positve
            {
                subtractedBigInt = this.privAdd(pSecondNumber);
                subtractedBigInt = new BigInt(subtractedBigInt.numberArray, true);
            }

            else if (this.sign == false && pSecondNumber.sign == true) //returns negative
            {
                subtractedBigInt = this.privAdd(pSecondNumber);
                subtractedBigInt = new BigInt(subtractedBigInt.numberArray, false);
            }
        }

        return subtractedBigInt;
    }

    private BigInt privSubtract (BigInt pSecondNumber)
    {
        BigInt differenceBigInt = null;
        byte[] subtractedArray;
        int sizeOfSubtractedArray = 0;
        int borrow = 0;
        int index = 0;
        byte[] firstNumArray = Arrays.copyOf(this.numberArray, this.numberArray.length);
        byte[] secondNumArray = Arrays.copyOf(pSecondNumber.numberArray, pSecondNumber.numberArray.length);


        if (firstNumArray.length > secondNumArray.length)
        {
            sizeOfSubtractedArray = firstNumArray.length; // The length of the new array will be the size of the bigger number
            secondNumArray = addLeadingZeroes(firstNumArray, secondNumArray);
        }
        else if (firstNumArray.length < secondNumArray.length)
        {
            sizeOfSubtractedArray = secondNumArray.length; // The length of the new array will be the size of the bigger number
            firstNumArray = addLeadingZeroes( secondNumArray, firstNumArray);
        }
        else
        {
            sizeOfSubtractedArray = firstNumArray.length; // If they're both the same size, the new array will be the same size as well.
        }
        subtractedArray = new byte[sizeOfSubtractedArray];


        for (index = subtractedArray.length-1; index >= 0; index--)
            if (firstNumArray[index] - secondNumArray[index] < 0)
            {
                borrow = 10;
                firstNumArray[index - 1] -= 1; // After borrowing, change the value of the number you borrowed from
                subtractedArray[index] = (byte) ((byte)(firstNumArray[index] + borrow) - secondNumArray[index]);

            }
            else
            {
                subtractedArray[index] = (byte) (firstNumArray[index] - secondNumArray[index]);
            }

        subtractedArray = removeLeadingZeroes(subtractedArray);
        differenceBigInt = new BigInt(subtractedArray, true);

        return differenceBigInt;
    }

    public BigInt multiply(BigInt pSecondNumber)
    {
        BigInt productBigInt = null;
        if (this.sign == true && pSecondNumber.sign == true)
        {
            productBigInt = privMultiply(pSecondNumber);
            productBigInt = new BigInt(productBigInt.numberArray, true);
        }

        else if (this.sign == false && pSecondNumber.sign == false)
        {
            productBigInt = privMultiply(pSecondNumber);
            productBigInt = new BigInt(productBigInt.numberArray, true);
        }

        else if (this.sign != pSecondNumber.sign)
        {
            productBigInt = privMultiply(pSecondNumber);
            productBigInt = new BigInt(productBigInt.numberArray, false);
        }

        return productBigInt;
    }

    private BigInt privMultiply(BigInt pSecondNumber)
    {

        BigInt productBigInt;
        // The length of the new product can only be as long as the addition of the two lengths
        int sizeOfProductArray = this.numberArray.length + pSecondNumber.numberArray.length;
        byte[] resultArray = new byte[sizeOfProductArray];
        byte[] firstArray = Arrays.copyOf(this.numberArray, this.numberArray.length); // Deep copy of both arrays
        byte[] secondArray = Arrays.copyOf(pSecondNumber.numberArray, pSecondNumber.numberArray.length); // Deep copy of both arrays
        int firstArrLength = firstArray.length;
        int secondArrLength = secondArray.length;
        int index = 1;
        int secondIndex = 0;
        int partialProduct;
        int carry;


        for (secondIndex = secondArrLength - 1; secondIndex >= 0; secondIndex--)
        {
            for (index = firstArrLength-1; index >= 0; index--)
            {
                partialProduct = firstArray[index] * secondArray[secondIndex];

                carry = resultArray[index + secondIndex + 1] + partialProduct;
                resultArray[index+secondIndex+1] = (byte)(carry%10);
                resultArray[index+secondIndex] = (byte)(resultArray[index+secondIndex] + carry/10);
            }
        }

        // Remove any leading zeroes
        resultArray = removeLeadingZeroes(resultArray);
        productBigInt = new BigInt(resultArray, true);
        return productBigInt;

    }

    // function that divides two BigInts using their arrays




    public BigInt divide(BigInt pSecondNumber) throws BigIntException
    {
        BigInt quotientBigInt = null;

        if (this.sign == true && pSecondNumber.sign == true)
        {
            quotientBigInt = privDivide(pSecondNumber);
            quotientBigInt = new BigInt(quotientBigInt.numberArray, true);
        }

        else if (this.sign == false && pSecondNumber.sign == false)
        {
            quotientBigInt = privDivide(pSecondNumber);
            quotientBigInt = new BigInt(quotientBigInt.numberArray, true);
        }

        else if (this.sign != pSecondNumber.sign)
        {
            quotientBigInt = privDivide(pSecondNumber);
            quotientBigInt = new BigInt(quotientBigInt.numberArray, false);
        }

        return quotientBigInt;
    }

    private BigInt privDivide(BigInt pSecondNumber) throws BigIntException
    {

        byte[] dividendArray = Arrays.copyOf(this.numberArray, this.numberArray.length);
        byte[] divisorArray = Arrays.copyOf(pSecondNumber.numberArray, pSecondNumber.numberArray.length);
        BigInt dividend = new BigInt(dividendArray, true);
        BigInt divisor = new BigInt(divisorArray, true);
        byte[] quotientArray = new byte[dividend.numberArray.length+10];
        BigInt quotientBigInt = new BigInt(quotientArray, true);
        byte[] partialDividendArray;
        BigInt partialDividendBigInt;
        int howManyTimesDoesItFit = 0;
        int index = 0;
        BigInt doesItFitBigInt;
        BigInt divisorProduct = new BigInt(divisor.numberArray, true);


        if (dividend.compareAbsoluteValues(divisor) < 0)
        {
            quotientArray = new byte[1];
            quotientArray[0] = 0;
        }

        else if (pSecondNumber.numberArray[0] == 0)
        {
            throw new BigIntException("Can not divide by 0");
        }

        else
        {
            partialDividendArray = Arrays.copyOf(dividend.numberArray, 1);
            partialDividendBigInt = new BigInt(partialDividendArray, true);
            for (index = 0; index < dividend.numberArray.length; index++)
            {
                if (divisorProduct.compareAbsoluteValues(partialDividendBigInt) > 0)
                {
                    quotientArray[index] = 0;


                    try {
                        partialDividendArray = Arrays.copyOf(partialDividendBigInt.numberArray, partialDividendBigInt.numberArray.length+1);
                        partialDividendArray[partialDividendArray.length-1] = dividend.numberArray[index+1];
                        partialDividendBigInt = new BigInt(partialDividendArray, true);
                    } catch (IndexOutOfBoundsException e) {
                        //break;
                    }

                }
                else
                {
                    while(divisorProduct.compareAbsoluteValues(partialDividendBigInt) <= 0)
                    {
                        howManyTimesDoesItFit++;
                        doesItFitBigInt = new BigInt("+"+howManyTimesDoesItFit);
                        divisorProduct = divisor.multiply(doesItFitBigInt);
                    }
                    //deiterate the variable as it counts one extra
                    howManyTimesDoesItFit = howManyTimesDoesItFit-1;

                    //set the quotient
                    quotientArray[index] = (byte)(howManyTimesDoesItFit);

                    //prepare the numbers for the next iteration of the for loop
                    doesItFitBigInt = new BigInt("+"+howManyTimesDoesItFit);
                    divisorProduct = divisor.multiply(doesItFitBigInt);

                    partialDividendBigInt = partialDividendBigInt.subtract(divisorProduct);
                    try {
                        partialDividendArray = Arrays.copyOf(partialDividendBigInt.numberArray, partialDividendBigInt.numberArray.length+1);
                        partialDividendArray[partialDividendArray.length-1] = dividend.numberArray[index+1];
                        partialDividendBigInt = new BigInt(partialDividendArray, true);
                    } catch (IndexOutOfBoundsException e) {
                        //break;
                    }

                    divisorProduct = divisor;
                    howManyTimesDoesItFit = 0;
                }
            }
        }

        quotientArray = Arrays.copyOf(quotientArray, index); // Removes trailing zeroes
        quotientArray = removeLeadingZeroes(quotientArray);
        quotientBigInt = new BigInt(quotientArray, true);
        return quotientBigInt;

    }

}