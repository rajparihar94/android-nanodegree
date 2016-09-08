package com.example;

import java.util.Random;

public class JokeProvider {
    String[] jokes;
    public JokeProvider(){
        jokes = new String[25];
        jokes[0]= "I hate when I am about to hug someone really sexy and my face hits the mirror.";
        jokes[1]="As long as there are tests, there will be prayer in schools.";
        jokes[2]="What did one ocean say to the other ocean? Nothing, they just waved.";
        jokes[3]="A bank is a place that will lend you money, if you can prove that you don’t need it.";
        jokes[4]="What’s the difference between a new husband and a new dog? After a year, the dog is still excited to see you.";
        jokes[5]="Why did the scientist install a knocker on his door? He wanted to win the No-bell prize!";
        jokes[6]="Money talks ...but all mine ever says is good-bye.";
        jokes[7]="I say no to alcohol, it just doesn’t listen.";
        jokes[8]="Why did the bee get married? Because he found his honey.";
        jokes[9]="Why is the man who invests all your money called a broker?";
        jokes[10]="My birth certificate was a letter of apology that my dad got from the condom company…";
        jokes[11]="Lottery: a tax on people who are bad at math.";
        jokes[12]="I'm not saying I hate you, but I would unplug your life support to charge my phone.";
        jokes[13]="Apparently I snore so loudly that it scares everyone in the car I'm driving.";
        jokes[14]="Do not argue with an idiot. He will drag you down to his level and beat you with experience.";
        jokes[15]="The world wide web needs internet, not Stephen Hawking.";
        jokes[16]="You park on a driveway and drive on a parkway.";
        jokes[17]="I'm great at multitasking. I can waste time, be unproductive, and procrastinate all at once.";
        jokes[18]="What happens when you mix human DNA with goat DNA? Kicked out of the petting zoo.";
        jokes[19]="If you think nobody cares whether you're alive, try missing a couple of payments.";
        jokes[20]="Just read that 4,153,237 people got married last year\n," +
                " not to cause any trouble but shouldn't that be an even number?";
        jokes[21]="If i had a dollar for every girl that found me unattractive, they would eventually find me attractive.";
        jokes[22]="I find it ironic that the colors red, white, and blue stand for freedom until they are flashing behind you.";
        jokes[23]="Today a man knocked on my door and asked for a small donation towards the local swimming pool. I gave him a glass of water. ";
        jokes[24]="Relationships are a lot like algebra. Have you ever looked at your X and wondered Y?";
    }

    Random r = new Random();
    public String getRandomJoke(){
        int i = r.nextInt(25);
        return jokes[i];
    }

    public String[] getJokes(){
        return jokes;
    }
}
