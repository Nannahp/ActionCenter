import { mount } from '@vue/test-utils';
import { describe, expect, it, vi } from 'vitest';
import LeftArrowIcon from '@/components/icons/LeftArrowIcon.vue';
import RightArrowIcon from '@/components/icons/RightArrowIcon.vue';
import MonthCalender from "../MonthCalender.vue";


vi.mock('@/components/icons/LeftArrowIcon.vue');
vi.mock('@/components/icons/RightArrowIcon.vue');

describe('MonthCalender.vue', () => {
    //Left arrow
    it('navigates to the previous month when the left arrow is clicked', async () => {
        const wrapper = mount(MonthCalender, {
            props: {
                dutyDays: [],
            },
        });

        const initialMonth = wrapper.vm.currentMonth;
        const initialYear = wrapper.vm.currentYear;

        //Simulate click
        const leftArrow = wrapper.findComponent(LeftArrowIcon);
        await leftArrow.vm.$emit('click');

        expect(wrapper.vm.currentMonth).toBe(initialMonth - 1 < 0 ? 11 : initialMonth - 1);
        expect(wrapper.vm.currentYear).toBe(initialMonth - 1 < 0 ? initialYear - 1 : initialYear);
    });

    //Right arrow
    it('navigates to the next month when the right arrow is clicked', async () => {
        const wrapper = mount(MonthCalender, {
            props: {
                dutyDays: [],
            },
        });

        const initialMonth = wrapper.vm.currentMonth;
        const initialYear = wrapper.vm.currentYear;

        const rightArrow = wrapper.findComponent(RightArrowIcon);
        await rightArrow.vm.$emit('click'); // Emit click event

        expect(wrapper.vm.currentMonth).toBe((initialMonth + 1) % 12);
        expect(wrapper.vm.currentYear).toBe(initialMonth + 1 > 11 ? initialYear + 1 : initialYear);
    });
});
