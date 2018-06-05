package com.ubs.opsit.interviews;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Test;

import static com.ubs.opsit.interviews.support.BehaviouralTestEmbedder.aBehaviouralTestRunner;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Acceptance test class that uses the JBehave (Gerkin) syntax for writing stories.  You should not need to
 * edit this class to complete the exercise, this is your definition of done.
 */
public class BerlinClockFixture {

    private TimeConverter berlinClock;
    private String theTime;

    @Test
    public void berlinClockAcceptanceTests() throws Exception {
        aBehaviouralTestRunner()
                .usingStepsFrom(this)
                .withStory("berlin-clock.story")
                .run();
    }

    @When("the time is $time")
    public void whenTheTimeIs(String time) {
        theTime = time;
    }

    @Then("the clock should look like $")
    public void thenTheClockShouldLookLike(String theExpectedBerlinClockOutput) {
    	berlinClock = new TimeConverter() {
			
			@Override
			public String convertTime(String aTime) {
				String berlinClockTime = null;
				if (aTime != null && !aTime.isEmpty()) {

					String timeArr[] = aTime.split(":");
					String headerRowValue = getHeaderRowOfBerlinClock(timeArr[2]);
					String firstRowValue = getFirstRowOfBerlinClock(timeArr[0]);
					String secondRowValue = getSecondRowOfBerlinClock(timeArr[0]);
					String thirdRowValue = getThirdRowOfBerlinClock(timeArr[1]);
					String fourthRowValue = getFourthRowOfBerlinClock(timeArr[1]);
				
					StringBuilder strb = new StringBuilder();
					strb.append("\n");
					strb.append(headerRowValue).append("\n");
					strb.append(firstRowValue).append("\n");
					strb.append(secondRowValue).append("\n");
					strb.append(thirdRowValue).append("\n");
					strb.append(fourthRowValue);
					berlinClockTime = strb.toString();
				
				} else {
					berlinClockTime = null;
				}

				return berlinClockTime;
			}
			
			private String getFourthRowOfBerlinClock(String string) {
				String berlinTimeVar = null;

				Integer hoursP2 = Integer.parseInt(string) % 5;
				if (hoursP2 > 0) {
					berlinTimeVar = getBerlinTimeFormat('Y', hoursP2, 4);
				} else {
					berlinTimeVar = getBerlinTimeFormat('R', 0, 4);
				}
				return berlinTimeVar;

			}

			private String getThirdRowOfBerlinClock(String string) {
				String berlinTimeVar = null;

				Integer hoursP2 = Integer.parseInt(string) / 5;

				if (hoursP2 > 0) {
					berlinTimeVar = getBerlinTimeFormat('Y', hoursP2, 11);
					int index = berlinTimeVar.lastIndexOf('Y');

					char[] charArr = berlinTimeVar.toCharArray();
					for (int i = 0; i < charArr.length; i++) {
						if ((i + 1) % 3 == 0) {
							charArr[i] = 'R';
							if (i == index)
								break;
						}

					}
					berlinTimeVar = String.valueOf(charArr);
				} else {
					berlinTimeVar = getBerlinTimeFormat('R', 0, 11);
				}

				return berlinTimeVar;

			}

			private String getHeaderRowOfBerlinClock(String string) {
				
				Integer hoursP1 = Integer.parseInt(string)%2;		
				return hoursP1 == 0 ? "Y" : "O";
			}

			public String getFirstRowOfBerlinClock(String hoursVar) {
				String berlinTimeValue = null;
				Integer hoursP1 = Integer.parseInt(hoursVar) / 5;
				//System.out.println("getFirstRowOfBerlinClock hoursP1 = " + hoursP1);
				if (hoursP1 > 0) {
					berlinTimeValue = getBerlinTimeFormat('R', hoursP1, 4);
				} else {
					berlinTimeValue = getBerlinTimeFormat('R', 0, 4);
				}
				return berlinTimeValue;
			}

			public String getSecondRowOfBerlinClock(String hoursVar) {
				String berlinTimeVar = null;

				Integer hoursP2 = Integer.parseInt(hoursVar);
				Integer remainingDigits = hoursP2 % 5;
				//System.out.println("getSecondRowOfBerlinClock remainingDigits = " + remainingDigits);
				if (remainingDigits > 0) {
					berlinTimeVar = getBerlinTimeFormat('R', remainingDigits, 4);
				} else {
					berlinTimeVar = getBerlinTimeFormat('R', 0, 4);
				}
				return berlinTimeVar;

			}


			private String getBerlinTimeFormat(Character replaceChar, int count, int length) {
				StringBuilder berlinClkNotation = new StringBuilder();
				for (int i = 0; i < length; i++) {
					berlinClkNotation.append("O");
				}
				char[] charArr = berlinClkNotation.toString().toCharArray();
				for (int i = 0; i < count; i++) {
					charArr[i] = replaceChar;
				}
				//System.out.println("getBerlinTimeFormat = " + String.valueOf(charArr));
				return String.valueOf(charArr);
			}

		}; 
        assertThat(berlinClock.convertTime(theTime)).isEqualTo(theExpectedBerlinClockOutput);
    }
}
