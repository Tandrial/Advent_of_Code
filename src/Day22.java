import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Day22 {

	private static int solve(List<String> s, boolean hard) {
		List<Wizard> wizards = new ArrayList<Wizard>();
		AtomicInteger minMana = new AtomicInteger(Integer.MAX_VALUE);
		wizards.add(new Wizard(50, 500, parseBoss(s)));
		while (wizards.size() > 0) {
			Wizard curr = wizards.remove(0);
			if (hard) {
				if (curr.hp-- <= 0)
					continue;
			}
			curr.applyEffect();
			for (int spell = 0; spell < Wizard.spells.length; spell++) {
				if (curr.canCast(spell)) {
					Wizard next = curr.clone();
					next.castSpell(spell);
					next.applyEffect();

					if (next.boss[0] <= 0) {
						minMana.set(Math.min(minMana.get(), next.mana_spend));
						wizards.removeIf(w -> w.mana_spend > minMana.get());
					} else {
						next.hp -= Math.max(1, next.boss[1] - next.armor);
						if (next.hp > 0 && next.mana > 0 && next.mana_spend < minMana.get())
							wizards.add(next);
					}
				}
			}
		}
		return minMana.get();
	}

	private static int[] parseBoss(List<String> s) {
		int[] boss = new int[2];
		for (int i = 0; i < boss.length; i++)
			boss[i] = Integer.valueOf(s.get(i).split(": ")[1]);
		return boss;
	}

	public static void main(String[] args) throws IOException {
		List<String> s = Files.readAllLines(Paths.get("./input/Day22_input.txt"));
		System.out.println("Part One = " + solve(s, false));
		System.out.println("Part Two = " + solve(s, true));
	}
}

class Wizard implements Cloneable {

	static int[][] spells = { { 53, 0 }, { 73, 0 }, { 113, 6 }, { 173, 6 }, { 229, 5 } };

	int hp, mana, armor, mana_spend;

	int[] active_effects = new int[3];
	int[] boss; // {hp, dmg}

	public Wizard(int hp, int mana, int[] boss) {
		this.hp = hp;
		this.mana = mana;
		this.boss = boss;
	}

	public boolean canCast(int i) {
		return mana >= spells[i][0] && (i < 2 || active_effects[i - 2] == 0);
	}

	public void castSpell(int i) {
		mana -= spells[i][0];
		mana_spend += spells[i][0];
		if (i == 0) { // Magic Missile
			boss[0] -= 4;
		} else if (i == 1) { // Drain
			hp += 2;
			boss[0] -= 2;
		} else { // active effect
			active_effects[i - 2] = spells[i][1];
		}
	}

	public void applyEffect() {
		for (int i = 0; i < active_effects.length; i++) {
			if (active_effects[i] > 0) {
				active_effects[i]--;
				if (i == 0) { // Shield
					armor = 7;
				} else if (i == 1) { // Poison
					boss[0] -= 3;
				} else if (i == 2) { // Recharge
					mana += 101;
				}
			} else if (i == 0)
				armor = 0;
		}
	}

	public Wizard clone() {
		Wizard neu = new Wizard(hp, mana, boss.clone());
		neu.armor = this.armor;
		neu.mana_spend = this.mana_spend;
		neu.active_effects = this.active_effects.clone();
		return neu;
	}
}
