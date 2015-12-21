import java.io.IOException;
import java.nio.file.*;
import java.util.List;

public class Day21 {

	private static int solve(List<String> s, boolean loose) {
		int[] boss = parseBoss(s);
		int minGold = Integer.MAX_VALUE;
		int maxGold = Integer.MIN_VALUE;
		for (int weapon = 0; weapon < Fighter.weapons.length; weapon++) {
			for (int armor = 0; armor < Fighter.armors.length; armor++) {
				for (int lring = 0; lring < Fighter.dmg_ring.length + Fighter.armor_ring.length; lring++) {
					for (int rring = 0; rring < Fighter.dmg_ring.length + Fighter.armor_ring.length; rring++) {
						if (lring != 0 && lring == rring)
							continue;
						Fighter me = new Fighter(100, weapon, armor, lring, rring);
						if (simulate(me, boss.clone()))
							minGold = Math.min(minGold, me.cost);
						else
							maxGold = Math.max(maxGold, me.cost);
					}
				}
			}
		}
		return loose ? maxGold : minGold;
	}

	private static int[] parseBoss(List<String> s) {
		int[] boss = new int[3];
		for (int i = 0; i < boss.length; i++) {
			boss[i] = Integer.valueOf(s.get(i).split(":")[1].trim());
		}
		return boss;
	}

	private static boolean simulate(Fighter me, int[] boss) {
		for (;;) {
			boss[0] -= Math.max(1, me.getAtt() - boss[2]);
			if (boss[0] <= 0)
				return true;
			me.hp -= Math.max(1, boss[1] - me.getDef());
			if (me.hp <= 0)
				return false;
		}
	}

	public static void main(String[] args) throws IOException {
		List<String> s = Files.readAllLines(Paths.get("./input/Day21_input.txt"));
		System.out.println("Part One = " + solve(s, false));
		System.out.println("Part Two = " + solve(s, true));
	}
}

class Fighter {

	static int[][] weapons = new int[][] { { 8, 4 }, { 10, 5 }, { 25, 6 }, { 40, 7 }, { 74, 8 } };
	static int[][] armors = new int[][] { { 0, 0 }, { 13, 1 }, { 31, 2 }, { 53, 3 }, { 75, 4 }, { 102, 5 } };
	static int[][] dmg_ring = new int[][] { { 0, 0 }, { 25, 1 }, { 50, 2 }, { 100, 3 } };
	static int[][] armor_ring = new int[][] { { 0, 0 }, { 20, 1 }, { 40, 2 }, { 80, 3 } };

	int hp;
	int weapon;
	int armor;
	int lring;
	int rring;
	int cost;

	public Fighter(int hp, int weapon, int armor, int lring, int rring) {
		this.hp = hp;
		this.weapon = weapon;
		this.armor = armor;
		this.rring = lring;
		this.lring = rring;
		cost = Fighter.weapons[weapon][0] + Fighter.armors[armor][0];
		cost += lring > 3 ? Fighter.armor_ring[lring - 4][0] : Fighter.dmg_ring[lring][0];
		cost += rring > 3 ? Fighter.armor_ring[rring - 4][0] : Fighter.dmg_ring[rring][0];
	}

	public int getDef() {
		int def = armors[armor][1];
		def += lring > 3 ? armor_ring[lring - 4][1] : 0;
		def += rring > 3 ? armor_ring[rring - 4][1] : 0;
		return def;
	}

	public int getAtt() {
		int att = weapons[weapon][1];
		att += lring <= 3 ? dmg_ring[lring][1] : 0;
		att += rring <= 3 ? dmg_ring[rring][1] : 0;
		return att;
	}
}
