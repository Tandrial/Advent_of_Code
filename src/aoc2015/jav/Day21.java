package aoc2015.jav;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

class Day21 {

  private static int solve(List<String> s, boolean loose) {
    int[] boss = parseBoss(s);
    int minGold = Integer.MAX_VALUE;
    int maxGold = Integer.MIN_VALUE;
    for (int weapon = 0; weapon < Fighter.weapons.length; weapon++)
      for (int armor = 0; armor < Fighter.armors.length; armor++)
        for (int lring = 0; lring < 2 * Fighter.dmg_rings.length; lring++)
          for (int rring = 0; rring < 2 * Fighter.dmg_rings.length; rring++) {
            if (lring != 0 && lring == rring)
              continue;
            Fighter me = new Fighter(100, weapon, armor, lring, rring);
            boolean won = Math.ceil(me.hp / Math.max(1.0f, boss[1] - me.getDef())) >= Math
              .ceil(boss[0] / Math.max(1.0f, me.getAtt() - boss[2]));
            if (won)
              minGold = Math.min(minGold, me.cost);
            else
              maxGold = Math.max(maxGold, me.cost);
          }
    return loose ? maxGold : minGold;
  }

  private static int[] parseBoss(List<String> s) {
    int[] boss = new int[3];
    for (int i = 0; i < boss.length; i++)
      boss[i] = Integer.valueOf(s.get(i).split(": ")[1]);
    return boss;
  }

  public static void main(String[] args) throws IOException {
    List<String> s = Files.readAllLines(Paths.get("./input/2015/Day21_input.txt"));
    System.out.println("Part One = " + solve(s, false));
    System.out.println("Part Two = " + solve(s, true));
  }
}

class Fighter {

  static final int[][] weapons = {{8, 4}, {10, 5}, {25, 6}, {40, 7}, {74, 8}};
  static final int[][] armors = {{0, 0}, {13, 1}, {31, 2}, {53, 3}, {75, 4}, {102, 5}};
  static final int[][] dmg_rings = {{0, 0}, {25, 1}, {50, 2}, {100, 3}};
  private static final int[][] armor_rings = {{0, 0}, {20, 1}, {40, 2}, {80, 3}};

  final int hp;
  private final int weapon;
  private final int armor;
  private final int lring;
  private final int rring;
  int cost;

  public Fighter(int hp, int weapon, int armor, int lring, int rring) {
    this.hp = hp;
    this.weapon = weapon;
    this.armor = armor;
    this.rring = lring;
    this.lring = rring;
    cost = Fighter.weapons[weapon][0] + Fighter.armors[armor][0];
    cost += lring > 3 ? Fighter.armor_rings[lring - 4][0] : Fighter.dmg_rings[lring][0];
    cost += rring > 3 ? Fighter.armor_rings[rring - 4][0] : Fighter.dmg_rings[rring][0];
  }

  int getDef() {
    int def = armors[armor][1];
    def += lring > 3 ? armor_rings[lring - 4][1] : 0;
    def += rring > 3 ? armor_rings[rring - 4][1] : 0;
    return def;
  }

  int getAtt() {
    int att = weapons[weapon][1];
    att += lring <= 3 ? dmg_rings[lring][1] : 0;
    att += rring <= 3 ? dmg_rings[rring][1] : 0;
    return att;
  }
}
